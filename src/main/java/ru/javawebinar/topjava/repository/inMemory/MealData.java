package ru.javawebinar.topjava.repository.inMemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealData {
    private Map<Integer, Meal> mealData = new ConcurrentHashMap<>();

    {
        mealData.put(MealsUtil.getCurrentId(), new Meal(MealsUtil.getNewId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealData.put(MealsUtil.getCurrentId(), new Meal(MealsUtil.getNewId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealData.put(MealsUtil.getCurrentId(), new Meal(MealsUtil.getNewId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealData.put(MealsUtil.getCurrentId(), new Meal(MealsUtil.getNewId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealData.put(MealsUtil.getCurrentId(), new Meal(MealsUtil.getNewId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealData.put(MealsUtil.getCurrentId(), new Meal(MealsUtil.getNewId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public Map<Integer, Meal> getMealData() {
        return mealData;
    }
}
