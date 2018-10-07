package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Meal {
    private final long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public Meal(final long id, final LocalDateTime dateTime, final String description, final int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal that = (Meal) o;

        return Objects.equals(this.id, that.id)
            && Objects.equals(this.dateTime, that.dateTime)
            && Objects.equals(this.description, that.description)
            && Objects.equals(this.calories, that.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, calories);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("dateTime = " + dateTime)
            .add("description = " + description)
            .add("calories = " + calories)
            .toString();
    }
}
