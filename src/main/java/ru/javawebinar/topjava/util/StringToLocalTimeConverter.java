package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

/**
 * Converter SPI from {@link String} to {@link LocalTime}
 */
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(final String source) {
        return DateTimeUtil.parseLocalTime(source);
    }
}
