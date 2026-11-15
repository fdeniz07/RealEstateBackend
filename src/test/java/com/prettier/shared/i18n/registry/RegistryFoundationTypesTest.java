package com.prettier.shared.i18n.registry;

import com.prettier.entity.enums.TextDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class RegistryFoundationTypesTest {

    @Test
    void languageCapabilityM1Values() {
        assertEquals(2, LanguageCapability.values().length);
        assertEquals(LanguageCapability.UI_MESSAGE, LanguageCapability.valueOf("UI_MESSAGE"));
        assertEquals(LanguageCapability.ADVERT_CONTENT, LanguageCapability.valueOf("ADVERT_CONTENT"));
    }

    @Test
    void languageDescriptorRecord() {
        LanguageDescriptor descriptor =
                new LanguageDescriptor(
                        "de",
                        null,
                        "German",
                        "Deutsch",
                        true,
                        true,
                        true,
                        TextDirection.LTR,
                        (short) 10);
        assertEquals("de", descriptor.code());
        assertEquals(TextDirection.LTR, descriptor.textDirection());
    }

    @Test
    void exceptionTypesAreRuntimeExceptions() {
        assertInstanceOf(
                RuntimeException.class, new InvalidLanguageCodeException("invalid"));
        assertInstanceOf(
                RuntimeException.class, new UnsupportedLanguageException("unsupported"));
    }
}