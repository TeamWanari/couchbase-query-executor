package com.wanari.utils.couchbase.parameter.builder;

import com.wanari.utils.couchbase.parameter.Parameter;

import java.util.function.Function;

public class ApplyPath extends BasePath {

    public ApplyPath(BasePath basePath) {
        super(basePath);
    }

    public <T> void andApply(Function<String, T> parser) {
        parameters.add(new Parameter<>(key, value, condition, expressionMethod, parser, type));
    }

    public void add() {
        parameters.add(new Parameter<String>(key, value, condition, expressionMethod, type));
    }
}
