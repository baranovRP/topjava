package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);

    Meal save(Meal meal, int userId);

    boolean delete(int id);

    boolean delete(int id, int userId);

    Meal get(int id);

    Meal get(int id, int userId);

    Collection<Meal> getAll();

    Collection<Meal> getAllBetween(LocalDate startDate, LocalDate endDate);

    Collection<Meal> getAllBetweenForUser(int userId, LocalDate startDate, LocalDate endDate);
}
