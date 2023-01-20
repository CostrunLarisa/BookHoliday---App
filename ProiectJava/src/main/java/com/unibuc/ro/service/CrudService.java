package com.unibuc.ro.service;


import java.util.List;

public interface CrudService<T> {
    T save(T t);

    List<T> findAll();

    T findById(Long id);

    void deleteById(Long id);

    T update(Long id, T t);
}
