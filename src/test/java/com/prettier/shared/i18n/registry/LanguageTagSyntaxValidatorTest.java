package com.prettier.shared.i18n.registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageTagSyntaxValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"de", "tr", "en", "en-gb", "de-at", "zh-hant"})
    void acceptsValidSyntax(String code) {
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax(code));
        LanguageTagSyntaxValidator.validateSyntax(code);
    }

    @Test
    void nullIsInvalid() {
        assertFalse(LanguageTagSyntaxValidator.isValidSyntax(null));
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageTagSyntaxValidator.validateSyntax(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "e", "abcd", "en--gb", "en-", "-en", "en_gb", "en gb"})
    void rejectsInvalidSyntax(String code) {
        assertFalse(LanguageTagSyntaxValidator.isValidSyntax(code));
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageTagSyntaxValidator.validateSyntax(code));
    }

    @Test
    void whitespaceOnlyInvalid() {
        assertFalse(LanguageTagSyntaxValidator.isValidSyntax("   "));
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageTagSyntaxValidator.validateSyntax("   "));
    }

    @Test
    void exceedingMaxLengthInvalid() {
        String tooLong = "de-abcdefgh-abcdefgh-abcdefgh-abcdefgh";
        assertFalse(LanguageTagSyntaxValidator.isValidSyntax(tooLong));
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageTagSyntaxValidator.validateSyntax(tooLong));
    }

    @Test
    void subtagExceedingEightCharactersInvalid() {
        assertFalse(LanguageTagSyntaxValidator.isValidSyntax("en-abcdefghi"));
        assertThrows(
                InvalidLanguageCodeException.class,
                () -> LanguageTagSyntaxValidator.validateSyntax("en-abcdefghi"));
    }

    @Test
    void enGbDistinctFromEn() {
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax("en"));
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax("en-gb"));
        assertFalse("en".equals("en-gb"));
    }

    @Test
    void deAtDistinctFromDe() {
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax("de"));
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax("de-at"));
        assertFalse("de".equals("de-at"));
    }

    @Test
    void syntaxValidationDoesNotImplyRegistryMembership() {
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax("fr"));
        assertTrue(LanguageTagSyntaxValidator.isValidSyntax("xx-unknown"));
    }
}