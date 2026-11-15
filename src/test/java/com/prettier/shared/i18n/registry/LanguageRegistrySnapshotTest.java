package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageRegistrySnapshotTest {

    @Test
    void emptySnapshot() {
        LanguageRegistrySnapshot snapshot = LanguageRegistrySnapshotBuilder.buildFromEntities(List.of());

        assertTrue(snapshot.languages().isEmpty());
        assertFalse(snapshot.findByCode("de").isPresent());
    }

    @Test
    void findByExistingAndMissingCode() {
        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(seed("de", (short) 10, true)));

        Optional<LanguageDescriptor> found = snapshot.findByCode("de");
        assertTrue(found.isPresent());
        assertEquals("de", found.get().code());

        assertTrue(snapshot.findByCode("fr").isEmpty());
        assertTrue(snapshot.findByCode(null).isEmpty());
    }

    @Test
    void internalMapIsImmutable() throws Exception {
        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(seed("de", (short) 10, true)));

        Field byCodeField = LanguageRegistrySnapshot.class.getDeclaredField("byCode");
        byCodeField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, LanguageDescriptor> byCode = (Map<String, LanguageDescriptor>) byCodeField.get(snapshot);

        assertThrows(UnsupportedOperationException.class, () -> byCode.put("xx", foundDescriptor("xx")));
    }

    @Test
    void languagesListIsImmutable() {
        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(seed("tr", (short) 20, true)));

        List<LanguageDescriptor> list = snapshot.languages();
        assertThrows(UnsupportedOperationException.class, () -> list.add(foundDescriptor("xx")));
    }

    @Test
    void inputCollectionChangesDoNotAffectSnapshot() {
        List<Language> source = new ArrayList<>();
        source.add(seed("en", (short) 30, true));

        LanguageRegistrySnapshot snapshot = LanguageRegistrySnapshotBuilder.buildFromEntities(source);
        assertEquals(1, snapshot.languages().size());

        source.add(seed("de", (short) 10, true));
        assertEquals(1, snapshot.languages().size());
        assertTrue(snapshot.findByCode("de").isEmpty());
    }

    @Test
    void snapshotContainsDescriptorsOnly() {
        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(
                        List.of(seed("de", (short) 10, true), seed("tr", (short) 20, false)));

        for (LanguageDescriptor descriptor : snapshot.languages()) {
            assertInstanceOf(LanguageDescriptor.class, descriptor);
        }
        assertEquals(2, snapshot.languages().size());
        assertTrue(snapshot.findByCode("tr").get().active() == false);
    }

    @Test
    void listAndLookupAreConsistent() {
        LanguageRegistrySnapshot snapshot =
                LanguageRegistrySnapshotBuilder.buildFromEntities(
                        List.of(seed("de", (short) 10, true), seed("tr", (short) 20, true)));

        for (LanguageDescriptor descriptor : snapshot.languages()) {
            LanguageDescriptor fromMap = snapshot.findByCode(descriptor.code()).orElseThrow();
            assertEquals(descriptor, fromMap);
        }
    }

    private static LanguageDescriptor foundDescriptor(String code) {
        return new LanguageDescriptor(
                code, null, "n", "n", true, true, true, TextDirection.LTR, (short) 1);
    }

    private static Language seed(String code, short sortOrder, boolean active) {
        return Language.builder()
                .code(code)
                .name(code.toUpperCase())
                .nativeName(code)
                .active(active)
                .uiSupported(true)
                .contentSupported(true)
                .textDirection(TextDirection.LTR)
                .sortOrder(sortOrder)
                .build();
    }
}