package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    boolean delete(Integer id, int userId);

    Meal get(Integer id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate);
}
