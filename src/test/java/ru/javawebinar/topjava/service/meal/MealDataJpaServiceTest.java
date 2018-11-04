package ru.javawebinar.topjava.service.meal;

import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class MealDataJpaServiceTest extends MealServiceTest {
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void getWithUser() {
        Meal meal = mealService.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(meal.getUser(), ADMIN);
    }

    @Test
    public void getWithUserNotFound() {
        thrown.expect(NotFoundException.class);
        mealService.getWithUser(MEAL1_ID, ADMIN_ID);
    }
}