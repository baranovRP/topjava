package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseDateOrGetDefault;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseTimeOrGetDefault;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("get all");
        return service.getAll(authUserId());
    }

    public List<MealWithExceed> getAllWithExceeded() {
        log.info("get all with exceeded");
        return this.getFilteredWithExceeded("", "", "", "");
    }

    public List<MealWithExceed> getFilteredWithExceeded(final String fromDate,
                                                        final String toDate,
                                                        final String fromTime,
                                                        final String toTime) {
        log.info("get all filtered with exceeded");
        LocalDate startDate = parseDateOrGetDefault(fromDate, LocalDate.MIN);
        LocalDate endDate = parseDateOrGetDefault(toDate, LocalDate.MAX);
        LocalTime startTime = parseTimeOrGetDefault(fromTime, LocalTime.MIN);
        LocalTime endTime = parseTimeOrGetDefault(toTime, LocalTime.MAX);
        List<Meal> meals = service.getAllBetween(authUserId(), startDate, endDate);
        return MealsUtil.getFilteredWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public Meal get(final int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(final Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(final int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(final Meal meal, final int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, id, authUserId());
    }
}