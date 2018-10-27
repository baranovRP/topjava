package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
    "classpath:spring/spring-app.xml",
    "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private static final Logger log = getLogger(MealServiceTest.class);
    private static List<String> results = new CopyOnWriteArrayList<>();

    static {
        SLF4JBridgeHandler.install();
    }

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            logInfo(description, "skipped", nanos);
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, "finished", nanos);
        }
    };
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Autowired
    private MealService service;

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String msg = String.format("Test %s %s, spent %d microseconds",
            testName, status, TimeUnit.NANOSECONDS.toMicros(nanos));
        results.add(msg);
        log.info(msg);
    }

    @AfterClass
    public static void tearDown() {
        log.info("");
        log.info("[TEST RESULT]");
        log.info("");
        for (String l : results) {
            log.info(l);
        }
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=100002");
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void create() {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() {
        thrown.expect(NoResultException.class);
        thrown.expectMessage("No entity found for query");
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=100002");
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() {
        assertMatch(service.getBetweenDates(
            LocalDate.of(2015, Month.MAY, 30),
            LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}