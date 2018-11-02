package ru.javawebinar.topjava.service.user;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JDBC)
public class UserJdbcServiceTest extends UserServiceTest {
    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }
}