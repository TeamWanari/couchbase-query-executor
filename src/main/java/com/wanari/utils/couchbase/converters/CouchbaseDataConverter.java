package com.wanari.utils.couchbase.converters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CouchbaseDataConverter<T> implements DataConverter<T> {
    private final ObjectMapper objectMapper;

    public CouchbaseDataConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T apply(HashMap hashMap, Class<T> clazz) {
        LinkedHashMap data = (LinkedHashMap) hashMap.get("data");
        data.put("id", hashMap.get("id"));
        return objectMapper.convertValue(data, clazz);
    }

}
