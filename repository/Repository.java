package com.mobile.kiril.tagnote.repository;

import com.mobile.kiril.tagnote.specifications.Specification;

import java.util.List;

public interface Repository<T> {
    void add(T item);

    void remove(T item);

    void update(T item);

    List query(Specification specification);
}
