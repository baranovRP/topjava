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

public class DeleteMealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DeleteMealServlet.class);

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        new MealService(new MealDaoInMemory()).delete(id);
        log.debug("Delete meal with id: {}", id);
        resp.sendRedirect(String.format("%s/meals", req.getContextPath()));
    }
}
