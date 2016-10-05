package com.wanari.utils.couchbase;

import com.couchbase.client.java.document.json.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CouchbaseFilter extends HashMap<String, Object> {

    public void putIfNotEmpty(String key, String value) {
        if(!StringUtils.isEmpty(value)) {
            put(key, value);
        }
    }

    public <T> void putIfNotEmptyAndApply(String key, String value, Function<String, T> parser) {
        if(!StringUtils.isEmpty(value)) {
            put(key, parser.apply(value));
        }
    }

    public <T> void putIfConditionAndApply(String key, String value, Function<T, Boolean> condition, Function<String, T> parser) {
        if(!StringUtils.isEmpty(value)) {
            T parsedValue = parser.apply(value);
            if(condition.apply(parsedValue)) {
                put(key, parsedValue);
            }
        }
    }

    public void putCustom(String param1, String param2, BiFunction<String, String, CouchbaseFilterEntry> function) {
        CouchbaseFilterEntry entry = function.apply(param1, param2);
        if(entry.isEmpty()) {
            put(entry.key, entry.value);
        }
    }

    public JsonObject toJsonObject() {
        return JsonObject.from(this);
    }
}
