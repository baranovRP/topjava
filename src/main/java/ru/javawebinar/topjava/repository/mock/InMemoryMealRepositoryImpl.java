package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal save(final Meal meal, final int userId) {
        return (meal.isNew() || isUsersOwnMeal(meal, userId)) ? this.save(meal) : null;
    }

    @Override
    public boolean delete(final int id) {
        return repository.remove(id) != null;
    }

    @Override
    public boolean delete(final int id, final int userId) {
        Meal meal = get(id);
        return isUsersOwnMeal(meal, userId) ? this.delete(id) : null;
    }

    @Override
    public Meal get(final int id) {
        return repository.get(id);
    }

    @Override
    public Meal get(final int id, final int userId) {
        Meal meal = get(id);
        return isUsersOwnMeal(meal, userId) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
            .sorted(Comparator
                .comparing(Meal::getDateTime).reversed()
                .thenComparing(Meal::getId))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllBetween(final LocalDate startDate, final LocalDate endDate) {
        return this.getAll().stream()
            .filter(m -> DateTimeUtil.isBetween(m.getDate(), startDate, endDate))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllBetweenForUser(final int userId, LocalDate startDate, final LocalDate endDate) {
        return this.getAllBetween(startDate, endDate).stream()
            .filter(m -> m.getUserId().equals(userId))
            .collect(Collectors.toList());
    }

    private boolean isUsersOwnMeal(final Meal meal, final int userId) {
        return (meal != null) && meal.getUserId() == userId;
    }
}

