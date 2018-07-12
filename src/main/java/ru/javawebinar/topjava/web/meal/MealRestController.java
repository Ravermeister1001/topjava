package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int id) {
        log.info("get by id {}", id);
        Meal meal = service.get(id, SecurityUtil.authUserId());
        validateUserWithException(meal.getUserId());
        return meal;
    }

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete by id {}", id);
        validateUserWithException(service.get(id, SecurityUtil.authUserId()).getUserId());
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        validateUserWithException(meal.getUserId());
        service.update(meal, SecurityUtil.authUserId());
    }

    public Collection<MealWithExceed> getAll() {
        log.info("getAll");
        return service.getAll(SecurityUtil.authUserId());
    }

    public List<MealWithExceed> getBetween(String startDateAsString, String endDateAsString, String startTimeAsString, String endTimeAsString) {
        LocalDate startDate;
        LocalDate endDate;
        LocalTime startTime;
        LocalTime endTime;
        try {
            startDate = LocalDate.parse(startDateAsString);
        } catch (DateTimeParseException e) {
            startDate = LocalDate.MIN;
        }
        try {
            endDate = LocalDate.parse(endDateAsString);
        } catch (DateTimeParseException e) {
            endDate = LocalDate.MAX;
        }
        try {
            startTime = LocalTime.parse(startTimeAsString);
        } catch (DateTimeParseException e) {
            startTime = LocalTime.MIN;
        }
        try {
            endTime = LocalTime.parse(endTimeAsString);
        } catch (DateTimeParseException e) {
            endTime = LocalTime.MAX;
        }
        log.info("getAll between {} - {} and {} - {}", startDateAsString, endDateAsString, startTimeAsString, endTimeAsString);
        return service.getBetween(startDate, endDate, startTime, endTime, SecurityUtil.authUserId());
    }
}