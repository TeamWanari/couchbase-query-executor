package com.wanari.utils.couchbase.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class SyncGatewayDataConverter<T> implements DataConverter<T> {

    private final ObjectMapper objectMapper;

    public SyncGatewayDataConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public T apply(HashMap hashMap, Class<T> clazz) {
        LinkedHashMap data = (LinkedHashMap) hashMap.get("data");
        data.put("_id", hashMap.get("id"));
        data.put("_rev", ((LinkedHashMap) data.get("_sync")).get("rev"));
        return objectMapper.convertValue(data, clazz);
    }

}
