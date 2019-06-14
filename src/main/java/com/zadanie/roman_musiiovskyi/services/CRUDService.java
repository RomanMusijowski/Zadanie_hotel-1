package com.zadanie.roman_musiiovskyi.services;

import java.util.List;

public interface CRUDService<T> {

    List<T> listAll();

    T getById(Long id);

    T createNew(T object);

    T putByDTO(Long id, T object);

    T patchByDTO(Long id, T object);

    void deleteById(Long id);
}
