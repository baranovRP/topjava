package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    private final AtomicLong counter = new AtomicLong(6);
    private MealDao mealDao;

    public MealService(final MealDao mealDao) {
        this.mealDao = mealDao;
    }

    public Meal findOne(final long id) {
        return mealDao.findOne(id);
    }

    public List<Meal> findAll() {
        log.debug("Find all meals");
        return mealDao.findAll();
    }

    public Meal add(final LocalDateTime dateTime, final String description, final int calories) {
        return mealDao.create(new Meal(counter.incrementAndGet(), dateTime, description, calories));
    }

    public Meal update(final Meal meal) {
        return mealDao.update(meal);
    }

    public void delete(final Meal entity) {
        mealDao.delete(entity);
    }

    public void delete(final long id) {
        mealDao.deleteById(id);
    }

    public List<MealWithExceed> findAllWithExceeded(final int caloriesPerDay) {
        log.debug("Find all meals with exceeded");
        return MealsUtil.getFilteredWithExceeded(
            mealDao.findAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }
}
