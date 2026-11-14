package com.prettier.shared.utils.slug;

import com.prettier.shared.exception.commons.InvalidSlugException;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Set;

/**
 * Deterministic, language-aware slug normalization for M1 (Flyway column: {@code adverts.slug}, varchar 200).
 */
@Component
public class SlugNormalizer {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 200;

    private static final String LANG_TR = "tr";
    private static final String LANG_DE = "de";

    private static final Set<String> SUPPORTED_LANGUAGES = Set.of(LANG_TR, LANG_DE);

    /**
     * @deprecated Use {@link #normalize(String, String)} with an explicit content language ({@code tr} or {@code de}).
     */
    @Deprecated(forRemoval = true)
    public String normalize(String input) {
        throw new InvalidSlugException(
                "Slug normalization requires a content language code; use normalize(input, language) with \"tr\" or \"de\"");
    }

    public String normalize(String input, String language) {
        if (input == null) {
            throw new InvalidSlugException("Slug input must not be null");
        }
        if (input.isBlank()) {
            throw new InvalidSlugException("Slug input must not be blank");
        }

        String contentLanguage = resolveContentLanguage(language);

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFKC);
        normalized = applyLanguageMappings(normalized, contentLanguage);
        normalized = normalized.toLowerCase(Locale.ROOT);
        normalized = replaceSeparatorsWithHyphens(normalized);
        normalized = stripInvalidCharacters(normalized);
        normalized = collapseHyphens(normalized);
        normalized = trimHyphens(normalized);

        validateResult(normalized);
        return normalized;
    }

    static String resolveContentLanguage(String language) {
        if (language == null) {
            throw new InvalidSlugException("Content language code must not be null");
        }
        if (language.isBlank()) {
            throw new InvalidSlugException("Content language code must not be blank");
        }
        String normalized = language.trim().toLowerCase(Locale.ROOT);
        if (!SUPPORTED_LANGUAGES.contains(normalized)) {
            throw new InvalidSlugException("Unsupported content language code: " + language);
        }
        return normalized;
    }

    private static String applyLanguageMappings(String value, String contentLanguage) {
        StringBuilder out = new StringBuilder(value.length() + 16);
        for (int offset = 0; offset < value.length(); ) {
            int codePoint = value.codePointAt(offset);
            offset += Character.charCount(codePoint);

            if (LANG_DE.equals(contentLanguage)) {
                switch (codePoint) {
                    case 'ß' -> out.append("ss");
                    case 'ä', 'Ä' -> out.append("ae");
                    case 'ö', 'Ö' -> out.append("oe");
                    case 'ü', 'Ü' -> out.append("ue");
                    default -> out.appendCodePoint(codePoint);
                }
            } else {
                switch (codePoint) {
                    case 'ğ', 'Ğ' -> out.append('g');
                    case 'ş', 'Ş' -> out.append('s');
                    case 'ı', 'İ', 'I' -> out.append('i');
                    case 'ç', 'Ç' -> out.append('c');
                    case 'ö', 'Ö' -> out.append('o');
                    case 'ü', 'Ü' -> out.append('u');
                    default -> out.appendCodePoint(codePoint);
                }
            }
        }
        return out.toString();
    }

    private static String replaceSeparatorsWithHyphens(String value) {
        StringBuilder out = new StringBuilder(value.length());
        boolean lastWasHyphen = false;
        for (int offset = 0; offset < value.length(); ) {
            int codePoint = value.codePointAt(offset);
            offset += Character.charCount(codePoint);

            if (isSeparator(codePoint)) {
                if (!lastWasHyphen) {
                    out.append('-');
                    lastWasHyphen = true;
                }
            } else {
                out.appendCodePoint(codePoint);
                lastWasHyphen = false;
            }
        }
        return out.toString();
    }

    private static boolean isSeparator(int codePoint) {
        if (Character.isWhitespace(codePoint)) {
            return true;
        }
        return switch (codePoint) {
            case '_', '.', ',', ';', ':', '!', '?', '\'', '"', '(', ')', '[', ']', '{', '}',
                    '/', '\\', '|', '@', '#', '$', '%', '^', '&', '*', '+', '=', '~', '`',
                    '\u00b7', '\u2022', '\u2026' -> true;
            default -> false;
        };
    }

    private static String stripInvalidCharacters(String value) {
        StringBuilder out = new StringBuilder(value.length());
        for (int offset = 0; offset < value.length(); ) {
            int codePoint = value.codePointAt(offset);
            offset += Character.charCount(codePoint);
            if (codePoint == '-') {
                out.append('-');
            } else if (codePoint >= 'a' && codePoint <= 'z') {
                out.append((char) codePoint);
            } else if (codePoint >= '0' && codePoint <= '9') {
                out.append((char) codePoint);
            }
        }
        return out.toString();
    }

    private static String collapseHyphens(String value) {
        StringBuilder out = new StringBuilder(value.length());
        boolean lastWasHyphen = false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '-') {
                if (!lastWasHyphen) {
                    out.append('-');
                    lastWasHyphen = true;
                }
            } else {
                out.append(c);
                lastWasHyphen = false;
            }
        }
        return out.toString();
    }

    private static String trimHyphens(String value) {
        int start = 0;
        int end = value.length();
        while (start < end && value.charAt(start) == '-') {
            start++;
        }
        while (end > start && value.charAt(end - 1) == '-') {
            end--;
        }
        return value.substring(start, end);
    }

    private static void validateResult(String slug) {
        if (slug.length() < MIN_LENGTH) {
            throw new InvalidSlugException(
                    "Normalized slug length must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
        }
        if (slug.length() > MAX_LENGTH) {
            throw new InvalidSlugException(
                    "Normalized slug length must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters");
        }
        boolean hasLetterOrDigit = false;
        for (int i = 0; i < slug.length(); i++) {
            char c = slug.charAt(i);
            if (c == '-') {
                continue;
            }
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                hasLetterOrDigit = true;
                break;
            }
        }
        if (!hasLetterOrDigit) {
            throw new InvalidSlugException("Normalized slug must contain at least one ASCII letter or digit");
        }
    }
}