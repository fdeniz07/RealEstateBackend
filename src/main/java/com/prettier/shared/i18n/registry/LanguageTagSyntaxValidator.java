package com.prettier.shared.i18n.registry;

import java.util.regex.Pattern;

/**
 * M1 BCP-inspired syntax subset (LANGUAGE-REGISTRY-DESIGN v0.1.1 §4.3 stage B). Not full RFC 5646.
 */
public final class LanguageTagSyntaxValidator {

    private static final int MAX_LENGTH = 35;
    private static final Pattern SYNTAX_PATTERN = Pattern.compile("^[a-z]{2,3}(-[a-z0-9]{2,8})*$");

    private LanguageTagSyntaxValidator() {}

    /**
     * @throws InvalidLanguageCodeException when the canonical tag does not match M1 syntax rules
     */
    public static void validateSyntax(String canonicalTag) {
        if (canonicalTag == null || canonicalTag.isEmpty()) {
            throw new InvalidLanguageCodeException("Language code must not be null or empty");
        }
        if (canonicalTag.length() > MAX_LENGTH) {
            throw new InvalidLanguageCodeException(
                    "Language code exceeds maximum length of " + MAX_LENGTH + " characters");
        }
        if (!SYNTAX_PATTERN.matcher(canonicalTag).matches()) {
            throw new InvalidLanguageCodeException("Language code is not a well-formed tag: " + canonicalTag);
        }
    }

    public static boolean isValidSyntax(String canonicalTag) {
        if (canonicalTag == null || canonicalTag.isEmpty()) {
            return false;
        }
        if (canonicalTag.length() > MAX_LENGTH) {
            return false;
        }
        return SYNTAX_PATTERN.matcher(canonicalTag).matches();
    }
}