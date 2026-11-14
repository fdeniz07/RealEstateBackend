package com.prettier.shared.exception.commons;

/**
 * Raised when a slug cannot be normalized to a valid value (IMPL-010-2).
 * HTTP mapping is handled by a later integration layer.
 */
public class InvalidSlugException extends RuntimeException {

    public InvalidSlugException(String message) {
        super(message);
    }
}