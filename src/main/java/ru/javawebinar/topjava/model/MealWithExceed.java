package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MealWithExceed that = (MealWithExceed) o;

        return Objects.equals(this.id, that.id)
            && Objects.equals(this.dateTime, that.dateTime)
            && Objects.equals(this.description, that.description)
            && Objects.equals(this.calories, that.calories)
            && Objects.equals(this.exceed, that.exceed);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, calories, exceed);
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