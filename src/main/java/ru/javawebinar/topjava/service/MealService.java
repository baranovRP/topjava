package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    private MealDao mealDao;

    public MealService(final MealDao mealDao) {
        this.mealDao = mealDao;
    }

    public Meal findOne(final long id) {
        return (Meal) mealDao.findOne(id);
    }

    public List<Meal> findAll() {
        log.debug("Find all meals");
        return mealDao.findAll();
    }

    public Meal add(final Meal meal) {
        return (Meal) mealDao.create(meal);
    }

    public Meal update(final Meal meal) {
        return (Meal) mealDao.update(meal);
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
