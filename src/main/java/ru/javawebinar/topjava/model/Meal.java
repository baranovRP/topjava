package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal() {
    }

    public Meal(final Meal meal) {
        this(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Meal setDateTime(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Meal setDescription(final String description) {
        this.description = description;
        return this;
    }

    public int getCalories() {
        return calories;
    }

    public Meal setCalories(final int calories) {
        this.calories = calories;
        return this;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
            "id=" + id +
            ", dateTime=" + dateTime +
            ", description='" + description + '\'' +
            ", calories=" + calories +
            '}';
    }
}
