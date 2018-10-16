package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(1, new ConcurrentHashMap<>());
        repository.put(2, new ConcurrentHashMap<>());
        MealsUtil.MEALS.forEach(m -> this.save(m, m.getId(), 1));
    }

    @Override
    public Meal save(final Meal meal, final Integer id, final int userId) {
        Map<Integer, Meal> items = repository.getOrDefault(userId, new HashMap<>());
        if (items.isEmpty()) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            items.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return items.computeIfPresent(id, (mId, oldMeal) -> meal);
    }

    @Override
    public boolean delete(final Integer id, final int userId) {
        return repository.getOrDefault(userId, new HashMap<>()).remove(id) != null;
    }

    @Override
    public Meal get(final Integer id, final int userId) {
        return repository.getOrDefault(userId, new HashMap<>()).get(id);
    }

    @Override
    public List<Meal> getAll(final int userId, LocalDate startDate,
                             final LocalDate endDate) {
        return repository.getOrDefault(userId, new HashMap<>()).values().stream()
            .filter(m -> DateTimeUtil.isBetween(m.getDate(), startDate, endDate))
            .sorted(Comparator
                .comparing(Meal::getDateTime).reversed()
                .thenComparing(Meal::getId))
            .collect(Collectors.toList());
    }
}

