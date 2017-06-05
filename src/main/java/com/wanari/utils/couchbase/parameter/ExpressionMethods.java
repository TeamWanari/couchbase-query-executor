package com.wanari.utils.couchbase.parameter;

import com.couchbase.client.java.query.dsl.Expression;

import static com.couchbase.client.java.query.dsl.Expression.x;

public class ExpressionMethods {

    public static Expression equals(String propertyKey, String key) {
        return x(propertyKey).eq("$" + key);
    }

    public static Expression notEquals(String propertyKey, String key) {
        return x(propertyKey).ne("$" + key);
    }

    public static Expression contains(String propertyKey, String key) {
        return x("CONTAINS(LOWER(" + propertyKey + "), LOWER($" + key + "))");
    }

    public static Expression in(String propertyKey, String key) {
        return x(propertyKey).in("$" + key);
    }

    public static Expression greaterThanOrEquals(String propertyKey, String key) {
        return x(propertyKey).gte("$" + key);
    }

    public static Expression lessThanOrEquals(String propertyKey, String key) {
        return x(propertyKey).lte("$" + key);
    }

    public static Expression isNull(String propertyKey, String key) {
        return x(propertyKey + " IS NULL");
    }

    public static Expression isNotNull(String propertyKey, String key) {
        return x(propertyKey + " IS NOT NULL");
    }

    public static Expression isMissing(String propertyKey, String key) {
        return x(propertyKey + " IS MISSING");
    }

    public static Expression isNullOrMissing(String propertyKey, String key) {
        return x(propertyKey + " IS NULL OR " + propertyKey + " IS MISSING");
    }
}
