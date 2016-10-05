package com.wanari.utils.couchbase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContext {

    @Bean
    public CouchbaseQueryExecutor couchbaseQueryExecutor() {
        return new CouchbaseQueryExecutor();
    }
}
