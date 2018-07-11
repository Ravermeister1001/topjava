package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal get(int id, int userId) {
        validateUserWithException(userId);
        return repository.get(id, userId);
    }

    @Override
    public Meal create(Meal meal, int userId) {
        validateUserWithException(userId);
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) {
        validateUserWithException(userId);
        repository.delete(id, userId);
    }

    @Override
    public void update(Meal meal, int userId) {
        validateUserWithException(userId);
        repository.save(meal, userId);
    }

    @Override
    public Collection<MealWithExceed> getAll(int userId) {
        validateUserWithException(userId);
        return MealsUtil.getWithExceeded(repository.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    @Override
    public List<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        validateUserWithException(userId);
        if (startDate == null) {
            startDate = LocalDate.MIN;
        }
        if (endDate == null) {
            endDate = LocalDate.MAX;
        }
        if (startTime == null) {
            startTime = LocalTime.MIN;
        }
        if (endTime == null) {
            endTime = LocalTime.MAX;
        }
        return MealsUtil.getWithExceeded(repository.getBetween(startDate, endDate, startTime, endTime, userId), SecurityUtil.authUserCaloriesPerDay());
    }
}