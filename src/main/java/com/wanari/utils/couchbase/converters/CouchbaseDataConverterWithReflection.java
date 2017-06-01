package com.wanari.utils.couchbase.converters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CouchbaseDataConverterWithReflection<T> implements DataConverterWithReflection<T> {

    private final ObjectMapper objectMapper;

    public CouchbaseDataConverterWithReflection(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T apply(HashMap hashMap, Class<T> clazz) {
        LinkedHashMap data = (LinkedHashMap) hashMap.get("data");

        T obj = objectMapper.convertValue(data, clazz);
        setId(obj, hashMap, clazz);

        return obj;
    }
}
