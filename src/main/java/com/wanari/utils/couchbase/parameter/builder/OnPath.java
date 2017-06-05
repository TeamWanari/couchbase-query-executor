package com.wanari.utils.couchbase.parameter.builder;

import com.couchbase.client.java.query.dsl.Expression;
import com.wanari.utils.couchbase.parameter.ExpressionMethods;
import com.wanari.utils.couchbase.parameter.Parameter;
import com.wanari.utils.couchbase.parameter.Parameters;

import java.util.function.BiFunction;

import static com.wanari.utils.couchbase.parameter.ParameterType.*;

public class OnPath extends BasePath {

    public OnPath(String key, Parameters parameters) {
        super(key, parameters);
    }

    public void isNull() {
        parameters.add(new Parameter<String>(key, value, ExpressionMethods::isNull, IS_NULL));
    }

    public void isNotNull() {
        parameters.add(new Parameter<String>(key, value, ExpressionMethods::isNotNull, IS_NOT_NULL));
    }

    public void isMissing() {
        parameters.add(new Parameter<String>(key, value, ExpressionMethods::isMissing, IS_MISSING));
    }

    public void isNullOrMissing() {
        parameters.add(new Parameter<String>(key, value, ExpressionMethods::isNullOrMissing, IS_MISSING_OR_NULL));
    }

    public ExpressionPath isNot(String value) {
        this.expressionMethod = ExpressionMethods::notEquals;
        this.value = value;
        this.type = NOT_EQUALS;
        return new ExpressionPath(this);
    }

    public ExpressionPath is(String value) {
        this.expressionMethod = ExpressionMethods::equals;
        this.value = value;
        this.type = EQUALS;
        return new ExpressionPath(this);
    }

    public ExpressionPath contains(String value) {
        this.expressionMethod = ExpressionMethods::contains;
        this.value = value;
        this.type = CONTAINS;
        return new ExpressionPath(this);
    }

    public ExpressionPath in(String value) {
        this.expressionMethod = ExpressionMethods::in;
        this.value = value;
        this.type = IN;
        return new ExpressionPath(this);
    }

    public ExpressionPath from(String value) {
        this.expressionMethod = ExpressionMethods::greaterThanOrEquals;
        this.value = value;
        this.type = GREATER_THAN_OR_EQUALS;
        return new ExpressionPath(this);
    }

    public ExpressionPath to(String value) {
        this.expressionMethod = ExpressionMethods::lessThanOrEquals;
        this.value = value;
        this.type = LESS_THAN_OR_EQUALS;
        return new ExpressionPath(this);
    }

    public ExpressionPath customExpression(String value, BiFunction<String, String, Expression> expressionMethod) {
        this.expressionMethod = expressionMethod;
        this.value = value;
        this.type = LESS_THAN_OR_EQUALS;
        return new ExpressionPath(this);
    }
}
