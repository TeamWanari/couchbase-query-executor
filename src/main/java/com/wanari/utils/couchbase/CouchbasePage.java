package com.wanari.utils.couchbase;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CouchbasePage<T> {
    public List<T> data;
    public long totalElements;
    public int pageNumber;
    public int size;
    public int totalPages;

    private CouchbasePage() {
    }

    public CouchbasePage(Pageable pageable) {
        pageNumber = pageable.getPageNumber();
        size = pageable.getPageSize();
    }

    private <U> CouchbasePage(CouchbasePage<U> other, Function<U, T> mapper) {
        data = other.data.stream().map(mapper).collect(Collectors.toList());
        totalElements = other.totalElements;
        pageNumber = other.pageNumber;
        size = other.size;
        totalPages = other.totalPages;
    }

    public void calculateTotalPages() {
        totalPages = ((int) totalElements - 1) / size + 1;
    }

    public <R> CouchbasePage<R> map(Function<T, R> mapper) {
        return new CouchbasePage<>(this, mapper);
    }
}
