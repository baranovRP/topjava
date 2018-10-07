package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MealsServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealsServlet.class);

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setAttribute("meals",
            new MealService(new MealDaoInMemory()).findAllWithExceeded(2000));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
