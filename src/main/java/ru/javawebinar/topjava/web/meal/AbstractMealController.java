package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return new ArrayList<>(service.getAll());
    }

    public List<Meal> getAll(final int userId, final LocalDate startDate,
                             final LocalDate endDate) {
        log.info("getAll");
        return new ArrayList<>(service.getAllBetween(userId, startDate, endDate));
    }

    public Meal get(final int id, final int userId) {
        log.info("get {}", id);
        return service.get(id, userId);
    }

    public Meal create(final Meal meal, final int userId) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(final int id, final int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(final Meal meal, final int userId) {
        Integer id = meal.getId();
        log.info("update {} with id={}", meal, id);
        if (id != null) {
            assureIdConsistent(meal, id);
        }
        service.update(meal, userId);
    }
}