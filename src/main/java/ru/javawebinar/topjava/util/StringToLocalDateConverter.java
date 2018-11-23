package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * Converter SPI from {@link String} to {@link LocalDate}
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(final String source) {
        return DateTimeUtil.parseLocalDate(source);
    }
}
