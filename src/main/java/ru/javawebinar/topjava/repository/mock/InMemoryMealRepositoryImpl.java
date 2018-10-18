package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS_USER_1.forEach(m -> this.save(m, 1));
        MealsUtil.MEALS_USER_2.forEach(m -> this.save(m, 2));
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
        Map<Integer, Meal> items =
            repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            items.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return items.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(final Integer id, final int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return !(meals == null || meals.remove(id) == null);
    }

    @Override
    public Meal get(final Integer id, final int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(final int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? new ArrayList<>() : new ArrayList<>(meals.values());
    }

    @Override
    public List<Meal> getAllBetween(final int userId, LocalDate startDate,
                                    final LocalDate endDate) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? new ArrayList<>() : meals.values().stream()
            .filter(m -> DateTimeUtil.isBetween(m.getDate(), startDate, endDate))
            .sorted(Comparator
                .comparing(Meal::getDateTime).reversed()
                .thenComparing(Meal::getId))
            .collect(Collectors.toList());
    }
}
