package com.prettier.shared.i18n.registry;

import java.util.Locale;

/**
 * Stage A canonicalization plus stage B syntax validation (IMPL-LREG-04.1).
 */
public final class LanguageCodeCanonicalizer {

    private LanguageCodeCanonicalizer() {}

    /**
     * Canonicalizes user or path input and validates M1 syntax. Does not perform registry lookup.
     *
     * @return lowercase canonical tag (e.g. {@code en-gb} remains distinct from {@code en})
     */
    public static String canonicalize(String raw) {
        if (raw == null) {
            throw new InvalidLanguageCodeException("Language code must not be null");
        }

        String trimmed = trimAsciiWhitespace(raw);
        if (trimmed.isEmpty()) {
            throw new InvalidLanguageCodeException("Language code must not be empty");
        }

        String[] subtags = trimmed.split("-", -1);
        for (String subtag : subtags) {
            if (subtag.isEmpty()) {
                throw new InvalidLanguageCodeException("Language code contains an empty subtag");
            }
        }

        StringBuilder canonical = new StringBuilder();
        for (int i = 0; i < subtags.length; i++) {
            if (i > 0) {
                canonical.append('-');
            }
            canonical.append(subtags[i].toLowerCase(Locale.ROOT));
        }

        String result = canonical.toString();
        LanguageTagSyntaxValidator.validateSyntax(result);
        return result;
    }

    private static String trimAsciiWhitespace(String value) {
        int start = 0;
        int end = value.length();
        while (start < end && isAsciiWhitespace(value.charAt(start))) {
            start++;
        }
        while (end > start && isAsciiWhitespace(value.charAt(end - 1))) {
            end--;
        }
        return value.substring(start, end);
    }

    private static boolean isAsciiWhitespace(char c) {
        return c == ' '
                || c == '\t'
                || c == '\n'
                || c == '\r'
                || c == '\f'
                || c == 0x0B;
    }
}