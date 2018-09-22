package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.StringJoiner;

public class UserMeal {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMeal(final LocalDateTime dateTime,
                    final String description, final int calories) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
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
        return getDateTime().toLocalDate();
    }

    public LocalTime getTime() {
        return getDateTime().toLocalTime();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMeal that = (UserMeal) o;

        return Objects.equals(this.dateTime, that.dateTime)
            && Objects.equals(this.description, that.description)
            && Objects.equals(this.calories, that.calories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("dateTime = " + dateTime)
            .add("description = " + description)
            .add("calories = " + calories)
            .toString();
    }
}
