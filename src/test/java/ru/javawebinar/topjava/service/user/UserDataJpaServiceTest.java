package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class UserDataJpaServiceTest extends UserServiceTest {
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private UserService userService;
    @Autowired
    private MealService mealService;

    @Test
    public void getWithMeals() {
        User user = userService.getWithMeals(ADMIN_ID);
        assertMatch(user.getMeals(), ADMIN_MEAL1, ADMIN_MEAL2);
    }

    @Test
    public void getWithMealsNotFound() {
        thrown.expect(NotFoundException.class);
        userService.getWithMeals(1);
    }

    @Test
    public void getWithEmptyMeals() {
        mealService.delete(ADMIN_MEAL_ID, ADMIN_ID);
        mealService.delete(ADMIN_MEAL_ID + 1, ADMIN_ID);
        User user = userService.getWithMeals(ADMIN_ID);
        assertThat(user.getMeals()).hasSize(0);
    }
}