package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (ValidationUtil.validateUser(userId)) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (ValidationUtil.validateUser(userId)) {
            return false;
        }
        return (repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        if (ValidationUtil.validateUser(userId)) {
            return null;
        }
        return repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        if (ValidationUtil.validateUser(userId)) {
            return null;
        }

        return repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .filter(m -> DateTimeUtil.isBetween(m.getDateTime().toLocalDate(), startDate, endDate))
                .filter(m -> DateTimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
    .collect(Collectors.toCollection(ArrayList::new));
    }
}

