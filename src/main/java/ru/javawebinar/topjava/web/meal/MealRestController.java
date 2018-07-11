package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int id, int userId) {
        log.info("get by id {} with user id {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("create meal {} with user id {}", meal, userId);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete by id {} with user id {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update {} with user id {}", meal, userId);
        service.update(meal, userId);
    }

    public Collection<MealWithExceed> getAll(int userId) {
        log.info("getAll with user id {}", userId);
        return service.getAll(userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("getAll between {} - {} and {} - {} with user id {} ", startDate, endDate, startTime, endTime, userId);
        return service.getBetween(startDate, endDate, startTime, endTime, userId);
    }
}