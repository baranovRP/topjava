package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseDateOrGetDefault;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseTimeOrGetDefault;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        log.info("Bean definition names: {}", Arrays.toString(appCtx.getBeanDefinitionNames()));
        AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
        adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
        mealRestController = appCtx.getBean(MealRestController.class);
        mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 510), 2);
        mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 29, 15, 0), "Обед", 1510), 2);
        mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 29, 21, 0), "Ужин", 510), 2);
        mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 1510), 2);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Integer mealId = Integer.valueOf(id);
        Meal meal = new Meal(id.isEmpty() ? null : mealId,
            LocalDateTime.parse(request.getParameter("dateTime")),
            request.getParameter("description"),
            Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRestController.update(meal, mealId);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                log.info("filter");
                LocalDate fromDate =
                    parseDateOrGetDefault(request.getParameter("fromDate"), LocalDate.MIN);
                LocalDate toDate =
                    parseDateOrGetDefault(request.getParameter("toDate"), LocalDate.MAX);
                LocalTime fromTime =
                    parseTimeOrGetDefault(request.getParameter("fromTime"), LocalTime.MIN);
                LocalTime toTime =
                    parseTimeOrGetDefault(request.getParameter("toTime"), LocalTime.MAX);
                List<MealWithExceed> allWithExceeded =
                    mealRestController.getFilteredWithExceeded(fromDate, toDate, fromTime, toTime);
                request.setAttribute("meals", allWithExceeded);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "cancel":
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAllWithExceeded());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
