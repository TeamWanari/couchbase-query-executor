package com.wanari.utils.couchbase.exceptions;

import com.couchbase.client.java.document.json.JsonObject;

public class NonUniqueResultException extends RuntimeException {
    public NonUniqueResultException(JsonObject params) {
        super("More than one document found with params: " + params.toString());
    }
}
