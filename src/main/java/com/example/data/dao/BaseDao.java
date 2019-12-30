package com.example.data.dao;

import java.util.List;

public interface BaseDao<T> {

    T create(T t); // C - create

    T get(long id); //R - read

    List<T> getAll(); //R - read

    T update(T t); //U - update

    void delete(long id); // D - delete
}