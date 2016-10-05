package com.wanari.utils.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.cluster.ClusterInfo;

public interface CouchbaseQueryExecutorConfiguration {
    ClusterInfo couchbaseClusterInfo() throws Exception;

    Bucket couchbaseClient() throws Exception;

    String getBucketName();
}
