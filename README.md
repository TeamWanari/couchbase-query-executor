# couchbase-query-executor

A library for filterable and sortable lists in SpringData with CouchBase using N1QL query. You can read more about this lib in [this blogpost](http://leaks.wanari.com/2016/10/24/couchbase-can-make-filterable-list-springdata/?utm_source=github&utm_medium=20161024&utm_campaign=suxy).

Stable with Couchbase 4.6.1 and Spring Boot 1.5.3

[![](https://jitpack.io/v/TeamWanari/couchbase-query-executor.svg)](https://jitpack.io/#TeamWanari/couchbase-query-executor)


## Installation

You can build dynamic N1QL queries in your SpringData application with this lib. Note that you can **only read** data with this library. If you want to insert/update/delete use the SyncGateway API.

Use https://jitpack.io/ to import this lib to your project and add the following lines to your pom.xml (as jitpack shows)

```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.TeamWanari</groupId>
    <artifactId>couchbase-query-executor</artifactId>
    <version>{version}</version>
</dependency>
```
After adding the dependency to your pom.xml, you have to implement the following interface:
```java
public interface CouchbaseQueryExecutorConfiguration {
    ClusterInfo couchbaseClusterInfo() throws Exception;

    Bucket couchbaseClient() throws Exception;

    String getBucketName();
}
```

### Example implementation:
##### CouchbaseConfiguration.java
```java
import com.wanari.utils.couchbase.CouchbaseQueryExecutorConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Collections;
import java.util.List;

@EnableCouchbaseRepositories("your.app.backend.repository")
@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration implements CouchbaseQueryExecutorConfiguration {

    @Value("${couchbase.cluster.bucket}")
    private String bucketName;

    @Value("${couchbase.cluster.password}")
    private String password;

    @Value("${couchbase.cluster.ip}")
    private String ip;

    @Override
    public String getBucketName() {
        return this.bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return this.password;
    }

    @Override
    protected List<String> getBootstrapHosts() {
        return Collections.singletonList(this.ip);
    }
}
```
##### application.yml
```yaml
couchbase:
  cluster:
    ip: 127.0.0.1
    bucket: dev
    password: superSecretPassword
```
## Usage and features
Basically, CouchbaseQueryExecutor provides you with functions to build and run queries dynamically. After running, the executor parses them to the POJO class that you gave as a parameter.

### Configuration

```yaml
couchbase-query-executor:
  with-sync-gateway: false  # Default is false
  use-default-id-fields: true #Default is true
```

If you are using Couchbase server with SyncGateway you should set the `couchbase-query-executor.with-sync-gateway` to `true`, so the executor can map `_rev` and `_id` to your entities.

If you are using the default naming convention for `id` fields (without SyncGateway it is `id` with SyncGateway it is `_id`) you should leave the `couchbase-query-executor.use-default-id-fields` flag on `true`. If you set that config to false Executor will find the field annotated with `com.couchbase.client.java.repository.annotation.Id` And use it as the id. I recommend you to use the default fields :)

### CouchbaseQueryExecutor functions
|**Name**|**Return type**|**Description**|
|---|---|---|
|`findOne(JsonObject params, Class<T> clazz)`|`Optional<T>`|Executes a query which gives back one document. Throws `NonUniqueResultException` if more than one document found|
|`find(JsonObject params, Pageable pageable, Class<T> clazz)`|`CouchbasePage<T>`|Executes a query which gives back a page of the matching documents|
|`find(JsonObject params, Class<T> clazz)`|`List<T>`|Executes a query which gives back a all of the matching documents|
|`public Integer count(JsonObject params)`|`Integer`|Executes a query which counts all of the matching documents|
|`sum(JsonObject params, String field)`|`Integer`|Executes a query which sums the results by the given field|

### How to add parameters to queries

First of all create a `new Parameters()` object, and you can use the builder pattenr is gives to build yout queries an easy way

|**BuilderPath**|**FunctionName**|**Returning / instantly add**|**Description**|
|---|---|---|---|
|Parameters|on(String key)|OnPath|You can provide the key of the parameter with this function. Executor is supporting nested querying, so `address.zipCode` means it will look up your document's `address`, then it's `zipCode` to execute the given condition on it|
|OnPath|isNull()|Adding parameter|Checks if the given key's value is null|
|OnPath|isNotNull()|Adding parameter|Checks if the given key's value is not null|
|OnPath|isMissing()|Adding parameter|Checks if the given key's value is missing|
|OnPath|isMissingOrNull()|Adding parameter|Checks if the given key's value is missing or null|
|OnPath|is(String value)|ExpressionPath|Checks whether the document's value on the path is equal to the value|
|OnPath|isNot(String value)|ExpressionPath|Checks whether the document's value on the path is NOT equal to the value|
|OnPath|contains(String value)|ExpressionPath|Checks whether the document's value on the path contains (ignoring the case) the value|
|OnPath|in(String value)|ExpressionPath|Checks whether the document's value is in the given array|
|OnPath|from(String value)|ExpressionPath|Checks whether the document's value on the path is greater than or equal to the value|
|OnPath|to(String value)|ExpressionPath|Checks whether the document's value on the path is less than or equal to the value|
|ExpressionPath|onlyIfNonEmpty()|ApplyPath|The expression will only appear in the query if the value is not empty|
|ExpressionPath|onlyIf(Predicate<String> condition)|ApplyPath|The expression will only appear in the query if the value matches the custom condition|
|ExpressionPath|andApply(Function<String, T> parser)|Adding parameter|Applies the given parser on the value|
|ExpressionPath|add()|Adding parameter|Adds the parameter|
|ApplyPath|andApply(Function<String, T> parser)|Adding parameter|Applies the given parser on the value|
|ApplyPath|add()|Adding parameter|Adds the parameter|

Also if you add the `CouchbaseQueryExecutor.IGNORE_CASE_ORDER` postfix to a sort param in the Pageable object, it will sort the documents ignoring case. Actually in SpringData, you have to provide this postfix in the query string, and the executor will check the ending of the parameter. Therefore:

## Final thoughts

This is not a final, nor a complete implementation, but it is useful if you want to build N1QL queries dynamically. I only implemented functions and filter postfixes that I needed in a specific application.

## Future

The code is provided as is. If I'll have time and need other use-cases, I'll extend the functionality. Feel free to contribute to it :)
