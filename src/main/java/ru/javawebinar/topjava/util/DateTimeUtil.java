package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    public static <T extends Comparable<T>> boolean isBetween(T lt, T startUnit, T endUnit) {
        return lt.compareTo(startUnit) >= 0 && lt.compareTo(endUnit) <= 0;
    }

    public static LocalDate parseDateOrGetDefault(final String unit,
                                                  final LocalDate defaultValue) {
        return isPresent(unit)
            ? LocalDate.parse(unit, DateTimeUtil.DATE_FORMATTER) : defaultValue;
    }

    public static LocalTime parseTimeOrGetDefault(final String unit,
                                                  final LocalTime defaultValue) {
        return isPresent(unit)
            ? LocalTime.parse(unit, DateTimeUtil.TIME_FORMATTER) : defaultValue;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    private static boolean isPresent(final String value) {
        return !value.equalsIgnoreCase("");
    }
}
