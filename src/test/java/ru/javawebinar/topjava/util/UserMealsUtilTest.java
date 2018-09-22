package ru.javawebinar.topjava.util;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserMealsUtilTest {

    private List<UserMeal> mealList;

    @Before
    public void setUp() {
        mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 20, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 20, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 20, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 21, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 21, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.SEPTEMBER, 21, 20, 0), "Ужин", 510)
        );
    }

    @Test
    public void getFilteredWithExceeded_FromEmptyList() {
        List filteredWithExceeded =
                UserMealsUtil.getFilteredWithExceeded(Collections.emptyList(),
                        LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> expected = Collections.emptyList();
        assertThat(filteredWithExceeded, is(expected));
    }

    @Test
    public void getFilteredWithExceeded() {
        List filteredWithExceeded =
                UserMealsUtil.getFilteredWithExceeded(mealList,
                        LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> expected = Arrays.asList(
                new UserMealWithExceed(LocalDateTime.of(2015, Month.SEPTEMBER, 20, 10, 0), "Завтрак", 500, false),
                new UserMealWithExceed(LocalDateTime.of(2015, Month.SEPTEMBER, 21, 10, 0), "Завтрак", 1000, true)
        );
        assertThat(filteredWithExceeded, is(expected));
    }

    @Test
    public void getFilteredWithExceededByCycle_FromEmptyList() {
        List filteredWithExceeded =
            UserMealsUtil.getFilteredWithExceeded(Collections.emptyList(),
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> expected = Collections.emptyList();
        assertThat(filteredWithExceeded, is(expected));
    }

    @Test
    public void getFilteredWithExceededByCycle() {
        List filteredWithExceeded =
            UserMealsUtil.getFilteredWithExceeded(mealList,
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        List<UserMealWithExceed> expected = Arrays.asList(
            new UserMealWithExceed(LocalDateTime.of(2015, Month.SEPTEMBER, 20, 10, 0), "Завтрак", 500, false),
            new UserMealWithExceed(LocalDateTime.of(2015, Month.SEPTEMBER, 21, 10, 0), "Завтрак", 1000, true)
        );
        assertThat(filteredWithExceeded, is(expected));
    }
}
