package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class UpdateMealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UpdateMealServlet.class);

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        long id = Long.parseLong(req.getParameter("id"));
        LocalDateTime dateTime = TimeUtil.parseDateTime(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(id, dateTime, description, calories);
        new MealService(new MealDaoInMemory()).update(meal);
        log.debug("Update meal: {}", meal);
        resp.sendRedirect(String.format("%s/meals", req.getContextPath()));
    }
}
