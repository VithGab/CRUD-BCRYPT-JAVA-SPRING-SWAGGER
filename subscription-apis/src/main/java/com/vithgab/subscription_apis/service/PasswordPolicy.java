package com.vithgab.subscription_apis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.vithgab.subscription_apis.exception.BusinessRuleException;

@Component 
public class PasswordPolicy {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 64;

    private static final Pattern UPPER = Pattern.compile("\\p{Lu}");
    private static final Pattern LOWER = Pattern.compile("\\p{Ll}");
    private static final Pattern DIGIT = Pattern.compile("\\p{Nd}");
    private static final Pattern SPECIAL = Pattern.compile("[^\\p{L}\\p{N}\\s]");
    private static final Pattern WHITESPACE = Pattern.compile("\\s");

    public void validate(String password, String name, String email) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.length() < MIN_LENGTH) {
            errors.add("Password must be at least " + MIN_LENGTH + " characters long.");
        } else {
            if (password.length() > MAX_LENGTH) {
                errors.add("Password must not exceed " + MAX_LENGTH + " characters.");
            }
            if (!UPPER.matcher(password).find()) {
                errors.add("Password must contain at least one uppercase letter.");
            }
            if (!LOWER.matcher(password).find()) {
                errors.add("Password must contain at least one lowercase letter.");
            }
            if (!DIGIT.matcher(password).find()) {
                errors.add("Password must contain at least one digit.");
            }
            if (!SPECIAL.matcher(password).find()) {
                errors.add("Password must contain at least one special character.");
            }
            if (WHITESPACE.matcher(password).find()) {
            errors.add("Password must not contain whitespace.");
            }
        }

        if (!errors.isEmpty()) {
            throw new BusinessRuleException("Password validation failed: ", errors);
        }

    }
}
