package ru.javawebinar.topjava.service.meal;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JDBC)
public class MealJdbcServiceTest extends MealServiceTest {
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }
}