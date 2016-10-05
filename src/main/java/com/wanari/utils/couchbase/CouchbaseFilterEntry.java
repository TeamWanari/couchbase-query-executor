package com.wanari.utils.couchbase;

public class CouchbaseFilterEntry {
    public String key;
    public Object value;
    private boolean isEmpty = false;

    public CouchbaseFilterEntry(String key, Object value) {
        this.key = key;
        this.value = value;
        this.isEmpty = true;
    }

    private CouchbaseFilterEntry() {
        this.isEmpty = false;
    }

    public static CouchbaseFilterEntry empty() {
        return new CouchbaseFilterEntry();
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
