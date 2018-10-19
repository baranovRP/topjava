package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID_1;
import static ru.javawebinar.topjava.UserTestData.USER_ID_2;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID_1, USER_ID_1);
        assertMatch(meal, MEAL_1_USER_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL_ID_1, USER_ID_2);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID_1, USER_ID_1);
        assertMatch(service.getAll(USER_ID_1), MEAL_6_USER_1, MEAL_5_USER_1,
            MEAL_4_USER_1, MEAL_3_USER_1, MEAL_2_USER_1);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() {
        service.delete(MEAL_ID_1, USER_ID_2);
    }


    @Test
    public void getBetweenDates() {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
            LocalDate.of(2015, Month.MAY, 31), USER_ID_1);
        assertMatch(all, MEAL_6_USER_1, MEAL_5_USER_1, MEAL_4_USER_1,
            MEAL_3_USER_1, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void getBetweenDatesTheSameDate() {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
            LocalDate.of(2015, Month.MAY, 30), USER_ID_1);
        assertMatch(all, MEAL_3_USER_1, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void getBetweenDatesEmptyDay() {
        List<Meal> all = service.getBetweenDates(LocalDate.of(2017, Month.MAY, 30),
            LocalDate.of(2017, Month.MAY, 31), USER_ID_1);
        assertMatch(all, Collections.emptyList());
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> all = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
            LocalDateTime.of(2015, Month.MAY, 30, 19, 0), USER_ID_1);
        assertMatch(all, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void getBetweenDateTimesTheSameDateTime() {
        List<Meal> all = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
            LocalDateTime.of(2015, Month.MAY, 30, 10, 0), USER_ID_1);
        assertMatch(all, MEAL_1_USER_1);
    }

    @Test
    public void getBetweenDateTimesEmptyDay() {
        List<Meal> all = service.getBetweenDateTimes(LocalDateTime.of(2017, Month.MAY, 30, 10, 0),
            LocalDateTime.of(2017, Month.MAY, 30, 19, 0), USER_ID_1);
        assertMatch(all, Collections.emptyList());
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID_1);
        assertMatch(all, MEAL_6_USER_1, MEAL_5_USER_1, MEAL_4_USER_1,
            MEAL_3_USER_1, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void getAllNonExistUser() {
        assertEquals(0, service.getAll(1).size());
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_1_USER_1);
        updated.setCalories(1500);
        updated.setDescription("Полудник");
        service.update(updated, USER_ID_1);
        assertMatch(service.get(MEAL_ID_1, USER_ID_1), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MEAL_1_USER_1);
        updated.setCalories(1500);
        updated.setDescription("Полудник");
        service.update(updated, USER_ID_2);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 700);
        Meal created = service.create(newMeal, USER_ID_1);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID_1), newMeal, MEAL_6_USER_1,
            MEAL_5_USER_1, MEAL_4_USER_1, MEAL_3_USER_1, MEAL_2_USER_1, MEAL_1_USER_1);
    }
}
