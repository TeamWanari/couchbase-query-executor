package com.wanari.utils.couchbase.exceptions;

import com.couchbase.client.java.document.json.JsonObject;

public class NonUniqueResultException extends RuntimeException {
    public NonUniqueResultException(JsonObject filters) {
        super("More than one document found with filters: " + filters.toString());
    }
}
