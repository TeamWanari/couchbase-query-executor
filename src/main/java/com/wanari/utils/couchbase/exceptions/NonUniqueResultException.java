package com.wanari.utils.couchbase.exceptions;

import com.wanari.utils.couchbase.parameter.Parameters;

public class NonUniqueResultException extends RuntimeException {
    public NonUniqueResultException(Parameters params) {
        super("More than one document found with params: " + params.toJsonObject().toString());
    }
}
