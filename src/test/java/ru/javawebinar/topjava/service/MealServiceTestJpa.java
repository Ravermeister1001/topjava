package ru.javawebinar.topjava.service;


import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db-test.xml"
})
public class MealServiceTestJpa extends AbstractMealServiceTest {
}
