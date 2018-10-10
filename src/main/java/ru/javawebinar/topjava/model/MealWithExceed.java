package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class MealWithExceed {
    private final long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(final long id, final LocalDateTime dateTime, final String description, final int calories, final boolean exceed) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("dateTime = " + dateTime)
            .add("description = " + description)
            .add("calories = " + calories)
            .add("exceed = " + exceed)
            .toString();
    }
}