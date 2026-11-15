package com.prettier.shared.i18n.registry;

/**
 * Syntactically valid tag that fails registry membership, active status or capability (stage C).
 * HTTP mapping is a later layer.
 */
public class UnsupportedLanguageException extends RuntimeException {

    public UnsupportedLanguageException(String message) {
        super(message);
    }
}