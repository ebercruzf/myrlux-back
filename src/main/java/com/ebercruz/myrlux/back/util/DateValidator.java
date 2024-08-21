package com.ebercruz.myrlux.back.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private static final Logger logger = LoggerFactory.getLogger(DateValidator.class);

    private ValidDate annotation;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //logger.debug("Validating date: {}", value);
        if (value == null || value.trim().isEmpty()) {
            //logger.debug("Date is null or empty, required: {}", annotation.required());
            return !annotation.required();
        }

        try {
            LocalDate date = LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);

            if (annotation.pastOrPresent() && date.isAfter(LocalDate.now())) {
                //logger.debug("Date is in the future, but pastOrPresent is required");
                return false;
            }

            if (annotation.past() && !date.isBefore(LocalDate.now())) {
                //logger.debug("Date is not in the past, but past is required");
                return false;
            }
            //logger.debug("Date is valid");
            return true;
        } catch (DateTimeParseException e) {
            //logger.debug("Failed to parse date", e);
            return false;
        }
    }

}
