# couchbase-query-executor

Library for filterable and sortable list in SpringData with CouchBase using N1QL query

## Installation

You can build dynamic N1QL queries in youe SpringData application with this lib.

Use https://jitpack.io/ to import it to your project and add the following lines to your pom.xml (as jitpack shows)

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
    <version>1.0</version>
</dependency>
```
After adding the dependency to your pom.xml, you have to implement the following interface
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
Basically CouchbaseQueryExecutor provides you functions to build queries dinamically and run them. After running the executor parses them to the POJO class that you gave as parameter.

### CouchbaseQueryExecutor functions
| **Name** | **Return type** | **Description** |
| --- | --- | --- |
| `find(JsonObject params, Pageable pageable, Class<T> clazz)` | `CouchbasePage<T>`	| Executes a query which gives back a page of the matching documents |
| `find(JsonObject params, Class<T> clazz)` | `List<T>` | Executes a query which gives back a all of the matching documents |
| `public Integer count(JsonObject params)` | `Integer` | Executes a query which counts all of the matching documents |
| `sum(JsonObject params, String field)` | `Integer` | Executes a query which sums the results by the given field |

There are constant postfixes defined in CouchbaseQueryExecutor that helps you to make the query you want. All you need is to append the postfix to your key, and CouchbaseQueryExecutor will do the work for you.

### CouchbaseQueryExecutor constant postfixes
| **Name** | **Description** | **Example** |
| --- | --- | --- |
| CONTAINS_FILTER | Compares the field and the given value ignoring the case | `filters.put("title" + CouchbaseQueryExecutor.CONAINS_FILTER, "a string")` |
| FROM_FILTER | Gives back documents that has higher value in the field | `filters.put("estimatedTime" + CouchbaseQueryExecutor.FROM_FILTER, 20)` |
| TO_FILTER | Gives back documents that has lower value in the field | `filters.put("estimatedTime" + CouchbaseQueryExecutor.TO_FILTER, 20)` |
| NOT_FILTER | Compares the field and the given value. Gives back the document if the field doesn't equal to the value | `filters.put("title" + CouchbaseQueryExecutor.NOT_FILTER, "i don't need this")` |
| IN_FILTER | Compares the field and the given value. Gives back the document any of the values match the field | `filters.put("age" + CouchbaseQueryExecutor.IN_FILTER, JsonArray.from(17, 18))` |

Also if you add the `CouchbaseQueryExecutor.IGNORE_CASE_ORDER` postfix to a sort param in the Pageable object, it will sort the documents ignoring the case. Actually in SpringData you have to provide this postfix in the query string, and the executor will check the ending of the parameter.