package com.vithgab.subscription_apis.exception;

import java.util.List;

public class BusinessRuleException extends RuntimeException {

    private final List<String> details;

    public BusinessRuleException(String message) {
        super(message);
        this.details = List.of();
    }

    public BusinessRuleException(String message, List<String> details) {
        super(message);
        this.details = List.copyOf(details);
    }

    public List<String> getDetails() {
        return details;
    }

}
