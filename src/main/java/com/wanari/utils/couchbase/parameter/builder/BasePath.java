package com.wanari.utils.couchbase.parameter.builder;

import com.couchbase.client.java.query.dsl.Expression;
import com.wanari.utils.couchbase.parameter.ParameterType;
import com.wanari.utils.couchbase.parameter.Parameters;

import java.util.function.BiFunction;
import java.util.function.Predicate;

class BasePath {
    String key;
    String value;
    ParameterType type;
    BiFunction<String, String, Expression> expressionMethod;
    Predicate<String> condition;
    Parameters parameters;

    BasePath(String key, Parameters parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    BasePath(BasePath other) {
        this.key = other.key;
        this.value = other.value;
        this.expressionMethod = other.expressionMethod;
        this.condition = other.condition;
        this.parameters = other.parameters;
        this.type = other.type;
    }
}
