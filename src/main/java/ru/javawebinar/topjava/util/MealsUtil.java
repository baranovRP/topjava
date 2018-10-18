package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static final List<Meal> MEALS_USER_1 = Arrays.asList(
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак-1", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед-1", 1000),
        new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин-1", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак-1", 1000),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед-1", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин-1", 510),
        new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин-1", 510),
        new Meal(LocalDateTime.of(2015, Month.MAY, 25, 10, 0), "Завтрак-1", 410),
        new Meal(LocalDateTime.of(2015, Month.MAY, 25, 15, 0), "Обед-1", 1410),
        new Meal(LocalDateTime.of(2015, Month.MAY, 25, 22, 0), "Ужин-1", 710)
    );
    public static final List<Meal> MEALS_USER_2 = Arrays.asList(
        new Meal(LocalDateTime.of(2015, Month.MAY, 20, 10, 0), "Завтрак-2", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 20, 13, 0), "Обед-2", 1000),
        new Meal(LocalDateTime.of(2015, Month.MAY, 20, 20, 0), "Ужин-2", 500),
        new Meal(LocalDateTime.of(2015, Month.MAY, 21, 20, 0), "Ужин-2", 510),
        new Meal(LocalDateTime.of(2015, Month.MAY, 21, 20, 0), "Ужин-2", 510),
        new Meal(LocalDateTime.of(2015, Month.MAY, 25, 10, 0), "Завтрак-2", 410),
        new Meal(LocalDateTime.of(2015, Month.MAY, 25, 15, 0), "Обед-2", 1410),
        new Meal(LocalDateTime.of(2015, Month.MAY, 25, 22, 0), "Ужин-2", 710)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceeded(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExceeded(meals, caloriesPerDay, meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime));
    }

    private static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
            .collect(
                Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
            );

        return meals.stream()
            .filter(filter)
            .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
            .collect(toList());
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}