package com.prettier.shared.i18n.registry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LanguageCodeCanonicalizerTest {

    @Test
    void canonicalizeTr() {
        assertEquals("tr", LanguageCodeCanonicalizer.canonicalize("TR"));
    }

    @Test
    void canonicalizeDeWithAsciiWhitespace() {
        assertEquals("de", LanguageCodeCanonicalizer.canonicalize(" DE "));
    }

    @Test
    void canonicalizeEnGb() {
        assertEquals("en-gb", LanguageCodeCanonicalizer.canonicalize("EN-GB"));
    }

    @Test
    void canonicalizeDeAt() {
        assertEquals("de-at", LanguageCodeCanonicalizer.canonicalize("DE-AT"));
    }

    @Test
    void canonicalizeEnLowercase() {
        assertEquals("en", LanguageCodeCanonicalizer.canonicalize("en"));
    }

    @Test
    void trimsAsciiTabAndNewline() {
        assertEquals("de", LanguageCodeCanonicalizer.canonicalize("\t\n de \r\n"));
    }

    @Test
    void regionalTagsRemainDistinctFromBase() {
        String en = LanguageCodeCanonicalizer.canonicalize("en");
        String enGb = LanguageCodeCanonicalizer.canonicalize("en-gb");
        String de = LanguageCodeCanonicalizer.canonicalize("de");
        String deAt = LanguageCodeCanonicalizer.canonicalize("de-at");
        assertNotEquals(en, enGb);
        assertNotEquals(de, deAt);
    }

    @Test
    void nullThrowsInvalidLanguageCodeException() {
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageCodeCanonicalizer.canonicalize(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void blankAfterTrimThrows(String raw) {
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageCodeCanonicalizer.canonicalize(raw));
    }

    @ParameterizedTest
    @ValueSource(strings = {"e", "abcd", "en--gb", "en-", "-en", "en_gb", "en gb"})
    void malformedTagsThrow(String raw) {
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageCodeCanonicalizer.canonicalize(raw));
    }

    @Test
    void exceedingMaxLengthThrows() {
        String tooLong = "de-abcdefgh-abcdefgh-abcdefgh-abcdefgh";
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageCodeCanonicalizer.canonicalize(tooLong));
    }

    @Test
    void subtagExceedingEightCharactersThrows() {
        assertThrows(InvalidLanguageCodeException.class, () -> LanguageCodeCanonicalizer.canonicalize("en-abcdefghi"));
    }
}