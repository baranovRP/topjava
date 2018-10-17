package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(final MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(final Meal meal, final int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(final int id, final int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(final int id, final int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void update(final Meal meal, final int id, final int userId) {
        checkNotFoundWithId(repository.save(meal, userId), id);
    }

    @Override
    public List<Meal> getAll(final int userId) {
        return repository.getAll(userId);
    }

    @Override
    public List<Meal> getAllBetween(final int userId, final LocalDate startDate,
                                    final LocalDate endDate) {
        return repository.getAllBetween(userId, startDate, endDate);
    }
}