package ru.javawebinar.topjava.dao;

import java.util.List;

/**
 * DAO interface.
 *
 * @param <T> type
 * @param <K> key
 */
public interface MealDao<T, K> {

    T findOne(K key);

    List<T> findAll();

    T create(T entity);

    T update(T entity);

    void deleteById(K key);
}
