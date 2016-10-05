package com.wanari.utils.couchbase;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class CouchbasePage<T> {
    public List<T> data;
    public long totalElements;
    public int pageNumber;
    public int size;
    public int totalPages;

    public CouchbasePage(Pageable pageable) {
        pageNumber = pageable.getPageNumber();
        size = pageable.getPageSize();
    }

    public void calculateTotalPages() {
        totalPages = ((int) totalElements - 1) / size + 1;
    }
}
