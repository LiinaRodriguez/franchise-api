package com.franchise.api.domain.exception;

import lombok.Getter;

@Getter
public class InvalidAttributeException extends RuntimeException {
    private final String attribute;

    public InvalidAttributeException(String attribute, String message) {
        super(message);
        this.attribute = attribute;
    }
}
