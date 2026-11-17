
package com.auca.validation;

import com.auca.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

public final class InputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{10,15}$");

    private InputValidator() {
    }

    public static String requireText(String value, String field)
            throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(field + " cannot be empty.");
        }
        return value.trim();
    }

    public static String validateLength(String value, String field,
                                        int min, int max)
            throws ValidationException {
        value = requireText(value, field);
        if (value.length() < min || value.length() > max) {
            throw new ValidationException(
                    field + " must contain between " + min + " and " + max + " characters."
            );
        }
        return value;
    }

    public static String validateEmail(String email, String field)
            throws ValidationException {
        email = requireText(email, field);
        if (email.length() > 120 || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Enter a valid " + field + ".");
        }
        return email;
    }

    public static String validatePhone(String phone, String field)
            throws ValidationException {
        phone = requireText(phone, field);
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new ValidationException(
                    field + " must contain 10-15 digits and may start with +."
            );
        }
        return phone;
    }

    public static LocalDate validatePastDate(LocalDate date, String field)
            throws ValidationException {
        if (date == null || !date.isBefore(LocalDate.now())) {
            throw new ValidationException(field + " must be a date in the past.");
        }
        return date;
    }

    public static LocalDate validateNotFutureDate(LocalDate date, String field)
            throws ValidationException {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new ValidationException(field + " cannot be in the future.");
        }
        return date;
    }

    public static int validateRange(int value, String field, int min, int max)
            throws ValidationException {
        if (value < min || value > max) {
            throw new ValidationException(
                    field + " must be between " + min + " and " + max + "."
            );
        }
        return value;
    }

    public static int validatePositive(int value, String field)
            throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(field + " must be greater than zero.");
        }
        return value;
    }

    public static int validateNonNegative(int value, String field)
            throws ValidationException {
        if (value < 0) {
            throw new ValidationException(field + " cannot be negative.");
        }
        return value;
    }

    public static BigDecimal validatePositiveAmount(
            BigDecimal amount, String field) throws ValidationException {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException(field + " must be greater than zero.");
        }
        return amount;
    }

    public static double validatePositive(double value, String field)
            throws ValidationException {
        if (!Double.isFinite(value) || value <= 0) {
            throw new ValidationException(field + " must be greater than zero.");
        }
        return value;
    }

    public static double validateScore(double score, double maximum)
            throws ValidationException {
        if (!Double.isFinite(score) || score < 0 || score > maximum) {
            throw new ValidationException(
                    "Obtained score must be between zero and the maximum score."
            );
        }
        return score;
    }

    public static void validateDateOrder(
            LocalDate start, LocalDate end, String message)
            throws ValidationException {
        if (start == null || end == null || !end.isAfter(start)) {
            throw new ValidationException(message);
        }
    }

    public static String validateAllowedValue(
            String value, String field, String... allowed)
            throws ValidationException {
        value = requireText(value, field).toUpperCase();

        for (String option : allowed) {
            if (option.equalsIgnoreCase(value)) {
                return value;
            }
        }

        throw new ValidationException(
                field + " must be one of: " + String.join(", ", allowed)
        );
    }
}
