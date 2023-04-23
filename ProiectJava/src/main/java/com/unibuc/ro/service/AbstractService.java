package com.unibuc.ro.service;

import com.unibuc.ro.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T> implements CrudService<T>{
    protected final JpaRepository<T, Long> repository;

    protected AbstractService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public T save(T t) {
        repository.save(t);
        return t;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(Long id) {
        Optional<T> object = repository.findById(id);
        if (object.isPresent()) {
            return object.get();
        } else {
            throw new EntityNotFoundException(getClass().toString().replaceAll("ServiceImpl" + "\\b|\\bclass com.unibuc.ro.service.","")
                    + " with id " + id.toString() + " not found!");
        }
    }
    @Override
    public void deleteById(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public T update(Long id, T t) {
        T object = findById(id);
        return save(t);
    }
}
