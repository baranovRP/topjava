package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    private static final String ID = "id";
    private static final String USER_ID = "userId";

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return findAndCheck(meal.getId(), userId) == null ? null : em.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
            .setParameter(ID, id)
            .setParameter(USER_ID, userId)
            .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return findAndCheck(id, userId);
    }

    private Meal findAndCheck(final int id, final int userId) {
        Meal meal = em.find(Meal.class, id);
        return (meal == null || meal.getUser().getId() != userId) ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
            .setParameter(USER_ID, userId)
            .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.BETWEEN_SORTED, Meal.class)
            .setParameter(USER_ID, userId)
            .setParameter("from", startDate)
            .setParameter("to", endDate)
            .getResultList();
    }
}