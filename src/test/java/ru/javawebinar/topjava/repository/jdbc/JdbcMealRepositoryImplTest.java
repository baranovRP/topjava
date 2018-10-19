package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID_1;
import static ru.javawebinar.topjava.UserTestData.USER_ID_2;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryImplTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealRepository repository;

    @Test
    public void saveCreate() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 700);
        Meal created = repository.save(newMeal, USER_ID_1);
        newMeal.setId(created.getId());
        assertMatch(repository.getAll(USER_ID_1), newMeal, MEAL_6_USER_1,
            MEAL_5_USER_1, MEAL_4_USER_1, MEAL_3_USER_1, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void saveUpdate() {
        Meal updated = new Meal(MEAL_1_USER_1);
        updated.setCalories(1500);
        updated.setDescription("Полудник");
        repository.save(updated, USER_ID_1);
        assertMatch(repository.get(MEAL_ID_1, USER_ID_1), updated);
    }

    @Test
    public void saveUpdateNotOwnMeal() {
        Meal updated = new Meal(MEAL_1_USER_1);
        updated.setCalories(1500);
        updated.setDescription("Полудник");
        Meal meal = repository.save(updated, USER_ID_2);
        assertThat(meal).isNull();
    }

    @Test
    public void delete() {
        repository.delete(MEAL_ID_1, USER_ID_1);
        assertMatch(repository.getAll(USER_ID_1), MEAL_6_USER_1, MEAL_5_USER_1,
            MEAL_4_USER_1, MEAL_3_USER_1, MEAL_2_USER_1);
    }

    @Test
    public void deletedNotOwnMeal() {
        boolean res = repository.delete(MEAL_ID_1, USER_ID_2);
        assertFalse(res);
    }

    @Test
    public void get() {
        Meal meal = repository.get(MEAL_ID_1, USER_ID_1);
        assertMatch(meal, MEAL_1_USER_1);
    }

    @Test
    public void getNotOwnMeal() {
        Meal meal = repository.get(MEAL_ID_1, USER_ID_2);
        assertThat(meal).isNull();
    }

    @Test
    public void getAll() {
        List<Meal> all = repository.getAll(USER_ID_1);
        assertMatch(all, MEAL_6_USER_1, MEAL_5_USER_1, MEAL_4_USER_1,
            MEAL_3_USER_1, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void getAllNonExistUser() {
        assertEquals(0, repository.getAll(1).size());
    }

    @Test
    public void getBetween() {
        List<Meal> all = repository.getBetween(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
            LocalDateTime.of(2015, Month.MAY, 30, 19, 0), USER_ID_1);
        assertMatch(all, MEAL_2_USER_1, MEAL_1_USER_1);
    }

    @Test
    public void getBetweenDateTimesTheSameDateTime() {
        List<Meal> all = repository.getBetween(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
            LocalDateTime.of(2015, Month.MAY, 30, 10, 0), USER_ID_1);
        assertMatch(all, MEAL_1_USER_1);
    }

    @Test
    public void getBetweenDateTimesEmptyDay() {
        List<Meal> all = repository.getBetween(LocalDateTime.of(2017, Month.MAY, 30, 10, 0),
            LocalDateTime.of(2017, Month.MAY, 30, 19, 0), USER_ID_1);
        assertMatch(all, Collections.emptyList());
    }
}
