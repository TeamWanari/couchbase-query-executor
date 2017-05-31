# couchbase-query-executor

A library for filterable and sortable lists in SpringData with CouchBase using N1QL query. You can read more about this lib in [this blogpost](http://leaks.wanari.com/2016/10/24/couchbase-can-make-filterable-list-springdata/?utm_source=github&utm_medium=20161024&utm_campaign=suxy).

Stable with Couchbase 4.6.1 and Spring Boot 1.5.3

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

### CouchbaseQueryExecutor functions
|**Name**|**Return type**|**Description**|
|---|---|---|
|`findOne(JsonObject params, Class<T> clazz)`|`Optional<T>`|Executes a query which gives back one document. Throws `NonUniqueResultException` if more than one document found|
|`find(JsonObject params, Pageable pageable, Class<T> clazz)`|`CouchbasePage<T>`|Executes a query which gives back a page of the matching documents|
|`find(JsonObject params, Class<T> clazz)`|`List<T>`|Executes a query which gives back a all of the matching documents|
|`public Integer count(JsonObject params)`|`Integer`|Executes a query which counts all of the matching documents|
|`sum(JsonObject params, String field)`|`Integer`|Executes a query which sums the results by the given field|

There are constant postfixes defined in CouchbaseQueryExecutor, helping you make the query you want. All you need is to append the postfix to your key, and CouchbaseQueryExecutor will do the work for you.

### CouchbaseQueryExecutor constant postfixes
|**Name**|**Description**|**Example**|
|---|---|---|
|CONTAINS_FILTER|Compares the field and the given value ignoring the case|`filters.put("title" + CouchbaseQueryExecutor.CONAINS_FILTER, "a string")`|
|FROM_FILTER|Gives back documents that has higher value in the field|`filters.put("estimatedTime" + CouchbaseQueryExecutor.FROM_FILTER, 20)`|
|TO_FILTER|Gives back documents that has lower value in the field|`filters.put("estimatedTime" + CouchbaseQueryExecutor.TO_FILTER, 20)`|
|NOT_FILTER|Compares the field and the given value. Gives back the document if the field doesn't equal to the value|`filters.put("title" + CouchbaseQueryExecutor.NOT_FILTER, "i don't need this")`|
|IN_FILTER|Compares the field and the given value. Gives back the document any of the values match the field|`filters.put("age" + CouchbaseQueryExecutor.IN_FILTER, JsonArray.from(17, 18))`|

Also if you add the `CouchbaseQueryExecutor.IGNORE_CASE_ORDER` postfix to a sort param in the Pageable object, it will sort the documents ignoring case. Actually in SpringData, you have to provide this postfix in the query string, and the executor will check the ending of the parameter. Therefore:

## Final thoughts

This is not a final, nor a complete implementation, but it is useful if you want to build N1QL queries dynamically. I only implemented functions and filter postfixes that I needed in a specific application.

## Future

The code is provided as is. If I'll have time and need other use-cases, I'll extend the functionality. Feel free to contribute to it :)
