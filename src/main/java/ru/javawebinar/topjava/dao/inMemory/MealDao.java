package ru.javawebinar.topjava.dao.inMemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inMemory.MealData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealDao {
    private Map<Integer, Meal> mealData = new MealData().getMealData();

    public Meal get(int id) {
        return mealData.get(id);
    }

    public void delete(int id) {
        mealData.remove(id);
    }

    public List<Meal> getAll() {
        return mealData.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public void saveOrUpdate(Meal meal) {
         mealData.put(meal.getId(), meal);
    }
}
