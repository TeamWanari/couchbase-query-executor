package com.wanari.utils.couchbase.parameter;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.dsl.Expression;
import com.wanari.utils.couchbase.parameter.builder.OnPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parameters {

    private List<Parameter> parameters = new ArrayList<>();

    public OnPath on(String key) {
        return new OnPath(key, this);
    }

    public List<Expression> toExpressions() {
        return parameters.stream()
            .filter(Parameter::ifCondition)
            .map(Parameter::toExpression)
            .collect(Collectors.toList());
    }

    public JsonObject toJsonObject() {
        Map<String, Object> map = new HashMap<>();

        parameters.stream()
            .filter(Parameter::ifCondition)
            .forEach(parameter -> map.put(parameter.getQueryKey(), parameter.getValue()));

        return JsonObject.from(map);
    }

    public <T> void add(Parameter<T> parameter) {
        parameters.add(parameter);
    }
}
