package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    /**
     * Filter meals with exceeded
     *
     * @param mealList       meals
     * @param startTime      startTime
     * @param endTime        endTime
     * @param caloriesPerDay calories
     * @return meals with exceeded
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded(final List<UserMeal> mealList,
                                                                   final LocalTime startTime,
                                                                   final LocalTime endTime,
                                                                   final int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = mealList.stream()
            .collect(groupingBy(UserMeal::getDate, summingInt(UserMeal::getCalories)));
        return mealList.stream()
            .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
            .map(meal -> new UserMealWithExceed(
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                dayCalories.get(meal.getDate()) > caloriesPerDay))
            .collect(Collectors.toList());
    }

    /**
     * Filter meals with exceeded based on cycles
     *
     * @param mealList       meals
     * @param startTime      startTime
     * @param endTime        endTime
     * @param caloriesPerDay calories
     * @return meals with exceeded
     */
    public static List<UserMealWithExceed> getFilteredWithExceededByCycles(final List<UserMeal> mealList,
                                                                           final LocalTime startTime,
                                                                           final LocalTime endTime,
                                                                           final int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            dayCalories.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> list = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(
                    meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    dayCalories.get(meal.getDate()) > caloriesPerDay);
                list.add(userMealWithExceed);
            }
        }
        return list;
    }
}
