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

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String MEALS = "meals";
    private final MealDaoInMemory mealDao = new MealDaoInMemory();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to {}", MEALS);
        req.setAttribute(MEALS,
            new MealService(mealDao).findAllWithExceeded(2000));
        req.getRequestDispatcher(MEALS + ".jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                add(req, resp);
                break;
            case "update":
                update(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            default:
                resp.sendRedirect(String.format("%s/%s", req.getContextPath(), MEALS));
                break;
        }
    }

    private void add(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        Meal food = extractFood(req);
        new MealService(mealDao).add(food);
        log.debug("Add new food");
        resp.sendRedirect(String.format("%s/%s", req.getContextPath(), MEALS));
    }

    private void update(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Meal meal = extractFood(req).setId(id);
        new MealService(mealDao).update(meal);
        log.debug("Update meal: {}", meal);
        resp.sendRedirect(String.format("%s/%s", req.getContextPath(), MEALS));
    }

    private void delete(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        new MealService(mealDao).delete(id);
        log.debug("Delete meal with id: {}", id);
        resp.sendRedirect(String.format("%s/%s", req.getContextPath(), MEALS));
    }

    private Meal extractFood(final HttpServletRequest req) {
        LocalDateTime dateTime = TimeUtil.parseDateTime(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        return new Meal(dateTime, description, calories);
    }
}
