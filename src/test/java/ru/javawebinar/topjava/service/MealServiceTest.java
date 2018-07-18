package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        final Meal meal = service.get(MEAL_1.getId(), USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        service.get(MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_1.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() {
        final List<Meal> meals = service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 30, 10, 0),
                LocalDateTime.of(2015, 5, 30, 14, 0), USER_ID);
        assertMatch(meals, MEAL_2, MEAL_1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = service.get(MEAL_1.getId(), USER_ID);
        updated.setDescription("new description");
        updated.setCalories(10);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_1.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        Meal updated = service.get(MEAL_1.getId(), USER_ID);
        updated.setDescription("new description");
        updated.setCalories(10);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2016, 6, 25, 11, 0), "new meal", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_3, MEAL_2, MEAL_1);
    }
}