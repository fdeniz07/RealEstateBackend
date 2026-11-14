package com.prettier.shared.utils.slug;

import com.prettier.shared.exception.commons.InvalidSlugException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlugNormalizerTest {

    private static final String TR = "tr";
    private static final String DE = "de";

    private SlugNormalizer normalizer;
    private Locale previousDefaultLocale;

    @BeforeEach
    void setUp() {
        normalizer = new SlugNormalizer();
        previousDefaultLocale = Locale.getDefault();
    }

    @AfterEach
    void restoreLocale() {
        Locale.setDefault(previousDefaultLocale);
    }

    @Test
    void deprecatedSingleArgumentRequiresLanguage() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("modern apartment"));
    }

    @Test
    void simpleLowercaseTitle() {
        assertEquals("modern-apartment", normalizer.normalize("modern apartment", TR));
    }

    @Test
    void uppercaseInput() {
        assertEquals("modern-apartment", normalizer.normalize("MODERN APARTMENT", TR));
    }

    @Test
    void leadingAndTrailingWhitespace() {
        assertEquals("modern-apartment", normalizer.normalize("   modern apartment   ", TR));
    }

    @Test
    void multipleSpaces() {
        assertEquals("modern-luxury-apartment", normalizer.normalize("modern    luxury   apartment", TR));
    }

    @Test
    void punctuation() {
        assertEquals("modern-apartment-center", normalizer.normalize("modern, apartment: center!", TR));
    }

    @Test
    void repeatedHyphensInInput() {
        assertEquals("modern-apartment", normalizer.normalize("modern---apartment", TR));
    }

    @Test
    void digits() {
        assertEquals("block-42-unit", normalizer.normalize("Block 42 Unit", TR));
    }

    @Test
    void mixedLettersAndDigits() {
        assertEquals("villa-2024-seaside", normalizer.normalize("Villa 2024 - Seaside", TR));
    }

    @Test
    void turkishGoztepeDeDaire() {
        assertEquals("goztepe-de-daire", normalizer.normalize("Göztepe'de Daire", TR));
    }

    @Test
    void turkishGuzelKonut() {
        assertEquals("guzel-konut", normalizer.normalize("Güzel Konut", TR));
    }

    @Test
    void turkishSikDaire() {
        assertEquals("sik-daire", normalizer.normalize("Şık Daire", TR));
    }

    @Test
    void turkishCagdasYasam() {
        assertEquals("cagdas-yasam", normalizer.normalize("Çağdaş Yaşam", TR));
    }

    @Test
    void turkishIsikApartmani() {
        assertEquals("isik-apartmani", normalizer.normalize("Işık Apartmanı", TR));
    }

    @Test
    void turkishIstanbulKonutlari() {
        assertEquals("istanbul-konutlari", normalizer.normalize("İstanbul Konutları", TR));
    }

    @Test
    void germanSchoeneWohnung() {
        assertEquals("schoene-wohnung", normalizer.normalize("Schöne Wohnung", DE));
    }

    @Test
    void germanGrossesHaus() {
        assertEquals("grosses-haus", normalizer.normalize("Großes Haus", DE));
    }

    @Test
    void germanMoeblierteKueche() {
        assertEquals("moeblierte-kueche", normalizer.normalize("Möblierte Küche", DE));
    }

    @Test
    void germanUeberDenDaechern() {
        assertEquals("ueber-den-daechern", normalizer.normalize("Über den Dächern", DE));
    }

    @Test
    void acceptsUppercaseLanguageCodeTr() {
        assertEquals("sik-daire", normalizer.normalize("Şık Daire", "TR"));
    }

    @Test
    void acceptsUppercaseLanguageCodeDe() {
        assertEquals("schoene-wohnung", normalizer.normalize("Schöne Wohnung", "DE"));
    }

    @Test
    void rejectsNullLanguage() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("Hello World", null));
    }

    @Test
    void rejectsBlankLanguage() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("Hello World", "   "));
    }

    @Test
    void rejectsUnsupportedLanguage() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("Hello World", "en"));
    }

    @Test
    void rejectsNullInput() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize(null, TR));
    }

    @Test
    void rejectsEmptyString() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("", TR));
    }

    @Test
    void rejectsWhitespaceOnly() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("   \t  ", TR));
    }

    @Test
    void rejectsOnlyPunctuation() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("..., !!!", TR));
    }

    @Test
    void rejectsFewerThanFiveCharacters() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("hi", TR));
    }

    @Test
    void acceptsExactlyFiveCharacters() {
        assertEquals("hello", normalizer.normalize("Hello", TR));
    }

    @Test
    void acceptsExactlyTwoHundredCharacters() {
        String twoHundred = "a".repeat(200);
        assertEquals(twoHundred, normalizer.normalize(twoHundred, TR));
    }

    @Test
    void rejectsMoreThanTwoHundredCharacters() {
        assertThrows(InvalidSlugException.class, () -> normalizer.normalize("a".repeat(201), TR));
    }

    @Test
    void unicodeCompatibilityCharacters() {
        assertEquals("office-suite", normalizer.normalize("oﬃce suite", TR));
    }

    @Test
    void sameOutputRegardlessOfDefaultJvmLocale() {
        String input = "İstanbul Konutları";
        String expected = "istanbul-konutlari";

        Locale.setDefault(Locale.GERMANY);
        assertEquals(expected, normalizer.normalize(input, TR));

        Locale.setDefault(Locale.forLanguageTag("tr-TR"));
        assertEquals(expected, normalizer.normalize(input, TR));

        Locale.setDefault(Locale.US);
        assertEquals(expected, normalizer.normalize(input, TR));
    }

    @Test
    void sameInputDiffersByContentLanguageForUmlauts() {
        assertEquals("goztepe-de-daire", normalizer.normalize("Göztepe'de Daire", TR));
        assertEquals("goeztepe-de-daire", normalizer.normalize("Göztepe'de Daire", DE));
    }
}