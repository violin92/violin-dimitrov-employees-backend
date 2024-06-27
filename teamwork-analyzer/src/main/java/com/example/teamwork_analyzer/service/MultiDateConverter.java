package com.example.teamwork_analyzer.service;

import com.opencsv.bean.AbstractBeanField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MultiDateConverter extends AbstractBeanField<LocalDate, String> {
    Logger logger = LoggerFactory.getLogger(MultiDateConverter.class);

    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy")
    );

    @Override
    protected Object convert(String value) {
        if (value == null || value.equalsIgnoreCase("NULL")) {
            return LocalDate.now();
        }

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }

        logger.error("Error parsing date: " + value);
        throw new RuntimeException("Invalid date format: " + value);
    }
}
