# couchbase-query-executor

Library for filterable and sortable list in SpringData with CouchBase using N1QL query

### Usage

You can build dynamic N1QL queries in youe SpringData application with this lib.

public <T> List<T> find(JsonObject params, Class<T> clazz) - returns all entities that match the params
 
public <T> CouchbasePage<T> find(JsonObject params, Pageable pageable, Class<T> clazz) - returns the entities uses pageable param for the limit and offset.

Use https://jitpack.io/ to import it to your project