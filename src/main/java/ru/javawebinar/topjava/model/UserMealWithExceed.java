package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public UserMealWithExceed(final LocalDateTime dateTime, final String description,
                              final int calories, final boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMealWithExceed that = (UserMealWithExceed) o;

        return Objects.equals(this.dateTime, that.dateTime)
            && Objects.equals(this.description, that.description)
            && Objects.equals(this.calories, that.calories)
            && Objects.equals(this.exceed, that.exceed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, description, calories, exceed);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("dateTime = " + dateTime)
            .add("description = " + description)
            .add("calories = " + calories)
            .add("exceed = " + exceed)
            .toString();
    }
}
