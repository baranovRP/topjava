package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController extends AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<Meal> getAll() {
        return super.getAll(authUserId());
    }

    public List<MealWithExceed> getAllWithExceeded() {
        return this.getFilteredWithExceeded(LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX);
    }

    public List<MealWithExceed> getFilteredWithExceeded(final LocalDate startDate,
                                                        final LocalDate endDate,
                                                        final LocalTime startTime,
                                                        final LocalTime endTime) {
        List<Meal> meals = super.getAll(authUserId(), startDate, endDate);
        return MealsUtil.getFilteredWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public Meal get(final int id) {
        return super.get(id, authUserId());
    }

    public Meal create(final Meal meal) {
        return super.create(meal, authUserId());
    }

    public void delete(final int id) {
        super.delete(id, authUserId());
    }

    public void update(final Meal meal, final int id) {
        super.update(meal, id, authUserId());
    }
}