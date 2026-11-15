package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageRegistrySnapshotBuilderTest {

    @Test
    void mapsEntityFieldsToDescriptor() {
        Language entity =
                Language.builder()
                        .code("de")
                        .name("German")
                        .nativeName("Deutsch")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 10)
                        .build();

        LanguageDescriptor descriptor = LanguageRegistrySnapshotBuilder.toDescriptor(entity);

        assertEquals("de", descriptor.code());
        assertEquals("German", descriptor.name());
        assertEquals("Deutsch", descriptor.nativeName());
        assertTrue(descriptor.active());
        assertTrue(descriptor.uiSupported());
        assertTrue(descriptor.contentSupported());
        assertEquals(TextDirection.LTR, descriptor.textDirection());
        assertEquals((short) 10, descriptor.sortOrder());
        assertNull(descriptor.parentCode());
    }

    @Test
    void parentBecomesParentCode() {
        Language parent = Language.builder().code("en").name("English").nativeName("English")
                .active(true).uiSupported(true).contentSupported(true)
                .textDirection(TextDirection.LTR).sortOrder((short) 30).build();

        Language child =
                Language.builder()
                        .code("en-gb")
                        .parent(parent)
                        .name("English (UK)")
                        .nativeName("English")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 35)
                        .build();

        LanguageRegistrySnapshot snapshot = LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(parent, child));

        assertEquals("en", snapshot.findByCode("en-gb").orElseThrow().parentCode());
        assertEquals("en-gb", snapshot.findByCode("en-gb").orElseThrow().code());
    }

    @Test
    void includesInactiveLanguages() {
        Language inactive =
                Language.builder()
                        .code("xx-inactive")
                        .name("Inactive")
                        .nativeName("Inactive")
                        .active(false)
                        .uiSupported(true)
                        .contentSupported(false)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 99)
                        .build();

        LanguageRegistrySnapshot snapshot = LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(inactive));

        assertEquals(1, snapshot.languages().size());
        assertTrue(snapshot.findByCode("xx-inactive").isPresent());
        assertTrue(!snapshot.findByCode("xx-inactive").orElseThrow().active());
    }

    @Test
    void sortsBySortOrderThenCode() {
        Language de = seed("de", (short) 20);
        Language tr = seed("tr", (short) 10);
        Language en = seed("en", (short) 10);

        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(de, en, tr));

        assertEquals(List.of("en", "tr", "de"), snapshot.languages().stream().map(LanguageDescriptor::code).toList());
    }

    @Test
    void rejectsDuplicateCodes() {
        Language first = seed("de", (short) 10);
        Language duplicate = seed("de", (short) 20);

        assertThrows(
                IllegalArgumentException.class,
                () -> LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(first, duplicate)));
    }

    @Test
    void rejectsNullCollection() {
        assertThrows(IllegalArgumentException.class, () -> LanguageRegistrySnapshotBuilder.buildFromEntities(null));
    }

    @Test
    void rejectsNullElement() {
        List<Language> withNull = new ArrayList<>();
        withNull.add(seed("de", (short) 10));
        withNull.add(null);

        assertThrows(
                IllegalArgumentException.class, () -> LanguageRegistrySnapshotBuilder.buildFromEntities(withNull));
    }

    @Test
    void builtSnapshotMapAndListAreConsistent() {
        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(
                        List.of(seed("de", (short) 10), seed("tr", (short) 20), seed("en", (short) 30)));

        assertEquals(3, snapshot.languages().size());
        assertEquals(List.of("de", "tr", "en"), snapshot.languages().stream().map(LanguageDescriptor::code).toList());
        for (LanguageDescriptor descriptor : snapshot.languages()) {
            assertEquals(descriptor, snapshot.findByCode(descriptor.code()).orElseThrow());
        }
    }

    private static Language seed(String code, short sortOrder) {
        return Language.builder()
                .code(code)
                .name(code)
                .nativeName(code)
                .active(true)
                .uiSupported(true)
                .contentSupported(true)
                .textDirection(TextDirection.LTR)
                .sortOrder(sortOrder)
                .build();
    }
}