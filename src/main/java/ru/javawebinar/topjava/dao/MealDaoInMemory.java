package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoInMemory implements MealDao {
    private static final Logger log = LoggerFactory.getLogger(MealDaoInMemory.class);
    public static Map<Long, Meal> meals = new ConcurrentHashMap<Long, Meal>() {
        {
            put(1L, new Meal(1L, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            put(2L, new Meal(2L, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            put(3L, new Meal(3L, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            put(4L, new Meal(4L, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            put(5L, new Meal(5L, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            put(6L, new Meal(6L, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }
    };
    private final AtomicLong counter = new AtomicLong(6);

    @Override
    public Meal findOne(final Long id) {
        log.debug("Find meal by id: {}", id);
        return meals.get(id);
    }

    @Override
    public List<Meal> findAll() {
        log.debug("Find all meals");
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal create(final Meal entity) {
        log.debug("Create meal: {}", entity);
        long id = counter.incrementAndGet();
        return meals.put(id, entity.setId(id));
    }

    @Override
    public Meal update(final Meal entity) {
        log.debug("Update meal: {}", entity);
        return meals.replace(entity.getId(), entity);
    }

    @Override
    public void delete(final Meal entity) {
        log.debug("Delete meal: {}", entity);
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(final Long key) {
        log.debug("Delete meal with id: {}", key);
        meals.remove(key);
    }
}
