package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("getting meals data");
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceededInOnePass(MealsUtil.getMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());
        req.setAttribute("meals", meals);

        log.debug("redirect to meals.jsp");
//        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        resp.sendRedirect("meals.jsp");
    }
}
