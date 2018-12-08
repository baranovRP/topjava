package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class FoodTo extends BaseTo implements Serializable {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime dateTime;

    @Size(min = 2, max = 255)
    @NotNull
    private String description;

    @Range(min = 10, max = 2000)
    @NotNull
    private int calories;

    public FoodTo() {
    }

    public FoodTo(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public FoodTo setDateTime(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FoodTo setDescription(final String description) {
        this.description = description;
        return this;
    }

    public int getCalories() {
        return calories;
    }

    public FoodTo setCalories(final int calories) {
        this.calories = calories;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FoodTo that = (FoodTo) o;

        return Objects.equals(this.calories, that.calories) &&
            Objects.equals(this.dateTime, that.dateTime) &&
            Objects.equals(this.description, that.description) &&
            Objects.equals(this.id, that.id);
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