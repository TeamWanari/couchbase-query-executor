package com.wanari.utils.couchbase.parameter.builder;

import com.wanari.utils.couchbase.parameter.Parameter;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;
import java.util.function.Predicate;

public class ExpressionPath extends BasePath {

    ExpressionPath(BasePath other) {
        super(other);
    }

    public ApplyPath onlyIfNonEmpty() {
        this.condition = StringUtils::isNotEmpty;
        return new ApplyPath(this);
    }

    public ApplyPath onlyIf(Predicate<String> condition) {
        this.condition = condition;
        return new ApplyPath(this);
    }

    public <T> void andApply(Function<String, T> parser) {
        parameters.add(new Parameter<>(key, value, condition, expressionMethod, parser, type));
    }

    public void add() {
        parameters.add(new Parameter<String>(key, value, condition, expressionMethod, type));
    }
}
