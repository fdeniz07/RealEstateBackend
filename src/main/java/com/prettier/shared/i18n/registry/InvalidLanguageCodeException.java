package com.prettier.shared.i18n.registry;

/**
 * Malformed or syntactically invalid language tag (stage A/B). HTTP mapping is a later layer.
 */
public class InvalidLanguageCodeException extends RuntimeException {

    public InvalidLanguageCodeException(String message) {
        super(message);
    }
}