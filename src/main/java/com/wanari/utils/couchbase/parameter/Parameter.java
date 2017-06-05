package com.wanari.utils.couchbase.parameter;

import com.couchbase.client.java.query.dsl.Expression;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Parameter<T> {
    private final String key;
    private final String value;
    private final Predicate<String> condition;
    private final BiFunction<String, String, Expression> expressionMethod;
    private final Function<String, T> parser;
    private final ParameterType type;

    public Parameter(String key, String value, Predicate<String> condition, BiFunction<String, String, Expression> expressionMethod, Function<String, T> parser, ParameterType type) {
        this.key = key;
        this.value = value;
        this.condition = condition;
        this.expressionMethod = expressionMethod;
        this.parser = parser;
        this.type = type;
    }

    public Parameter(String key, String value, Predicate<String> condition, BiFunction<String, String, Expression> expressionMethod, ParameterType type) {
        this.key = key;
        this.value = value;
        this.condition = condition;
        this.expressionMethod = expressionMethod;
        this.parser = null;
        this.type = type;
    }

    public Parameter(String key, String value, BiFunction<String, String, Expression> expressionMethod, ParameterType type) {
        this.key = key;
        this.value = value;
        this.type = type;
        this.condition = null;
        this.expressionMethod = expressionMethod;
        this.parser = null;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return parse(value);
    }

    public boolean ifCondition() {
        if(condition == null) {
            return true;
        } else {
            return condition.test(value);
        }
    }

    public Expression toExpression() {
        return expressionMethod.apply(key, getQueryKey());
    }

    private T parse(String value) {
        if(parser != null) {
            return parser.apply(value);
        } else {
            return (T) value;
        }
    }

    public String getQueryKey() {
        if(key != null) {
            return key.replaceAll("[\\\\.]", "_") + "_" + type.name();
        } else {
            return null;
        }
    }
}
