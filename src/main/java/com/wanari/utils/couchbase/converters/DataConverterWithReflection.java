package com.wanari.utils.couchbase.converters;

import com.couchbase.client.java.repository.annotation.Id;

import java.util.Arrays;
import java.util.HashMap;

public interface DataConverterWithReflection<T> extends DataConverter<T> {
    default void setId(T obj, HashMap hashMap, Class<T> clazz) {
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            if(field.getAnnotation(Id.class) != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, hashMap.get("id"));
                } catch(IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
