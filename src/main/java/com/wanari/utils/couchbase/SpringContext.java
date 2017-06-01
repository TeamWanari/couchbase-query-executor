package com.wanari.utils.couchbase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContext {

    @Bean
    public CouchbaseQueryExecutor couchbaseQueryExecutor(CouchbaseQueryExecutorConfiguration couchbaseConfiguration, ObjectMapper objectMapper) {
        return new CouchbaseQueryExecutor(couchbaseConfiguration, objectMapper);
    }
}
