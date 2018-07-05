package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.inMemory.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealDao mealDao = new MealDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String forward = "";

         if (action.equalsIgnoreCase("getAll")) {
            log.debug("getting meals data");
            List<MealWithExceed> meals = MealsUtil.getFilteredWithExceededInOnePass(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());
            req.setAttribute("meals", meals);

            log.debug("redirect to meals.jsp");
            forward = "meals.jsp";
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("getting meal id");
            int mealId = Integer.parseInt(req.getParameter("id"));

            log.debug("deleting meal by id");
            mealDao.delete(mealId);

            log.debug("getting meals data");
            List<MealWithExceed> meals = MealsUtil.getFilteredWithExceededInOnePass(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay());
            req.setAttribute("meals", meals);

            log.debug("redirecting to meals.jsp");
            forward = "meals.jsp";
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("getting meal id");
            int mealId = Integer.parseInt(req.getParameter("id"));

            log.debug("getting meal data and setting attribute");
            Meal meal = mealDao.get(mealId);
            req.setAttribute("meal", meal);

            log.debug("redirecting to meal.jsp");
            forward = "meal.jsp";
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal = new Meal(LocalDateTime.parse(req.getParameter("dateTime")), req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        if (!("".equals(req.getParameter("id")))) {
            meal.setId(Integer.parseInt(req.getParameter("id")));
        } else {
            meal.setId(MealsUtil.getNewId());

        }
        mealDao.saveOrUpdate(meal);
//        resp.sendRedirect("meals.jsp");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/meals.jsp");
        req.setAttribute("meals", MealsUtil.getFilteredWithExceededInOnePass(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.getDefaultCaloriesPerDay()));
        dispatcher.forward(req, resp);
    }
}
