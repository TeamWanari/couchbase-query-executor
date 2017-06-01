package com.wanari.utils.couchbase.converters;

import java.util.HashMap;
import java.util.function.BiFunction;

@FunctionalInterface
public interface DataConverter<R> extends BiFunction<HashMap, Class<R>, R> {
}
