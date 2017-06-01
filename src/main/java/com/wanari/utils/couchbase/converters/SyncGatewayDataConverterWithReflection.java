package com.wanari.utils.couchbase.converters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class SyncGatewayDataConverterWithReflection<T> implements DataConverterWithReflection<T> {

    private final ObjectMapper objectMapper;

    public SyncGatewayDataConverterWithReflection(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T apply(HashMap hashMap, Class<T> clazz) {
        LinkedHashMap data = (LinkedHashMap) hashMap.get("data");
        data.put("_rev", ((LinkedHashMap) data.get("_sync")).get("rev"));

        T obj = objectMapper.convertValue(data, clazz);
        setId(obj, hashMap, clazz);

        return obj;
    }
}
