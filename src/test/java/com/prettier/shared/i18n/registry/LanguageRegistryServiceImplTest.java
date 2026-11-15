package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import com.prettier.repository.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageRegistryServiceImplTest {

    @Mock private LanguageRegistrySnapshotLoader snapshotLoader;

    @Mock private LanguageRepository languageRepository;

    private LanguageRegistryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new LanguageRegistryServiceImpl(snapshotLoader);
    }

    @Test
    void currentSnapshotInitiallyEmptyButNonNull() {
        assertNotNull(service.currentSnapshot());
        assertTrue(service.currentSnapshot().languages().isEmpty());
    }

    @Test
    void canonicalizedLookup() {
        installSnapshot(
                seed("de", (short) 10, true, true, true), seed("tr", (short) 20, true, true, true));

        assertEquals("de", service.canonicalizeCode(" DE "));
        assertTrue(service.findByCode("TR").isPresent());
        assertEquals("tr", service.findByCode("TR").orElseThrow().code());
    }

    @Test
    void exactRegionalLookupWithoutParentFallback() {
        Language parent = seedEntity("en", (short) 30, true, true, true);
        Language child =
                Language.builder()
                        .code("en-gb")
                        .parent(parent)
                        .name("English UK")
                        .nativeName("English")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 35)
                        .build();
        installSnapshot(List.of(parent, child));

        LanguageDescriptor regional =
                service.requireActive("EN-GB", LanguageCapability.ADVERT_CONTENT);
        assertEquals("en-gb", regional.code());
        assertEquals("en", regional.parentCode());

        LanguageDescriptor base = service.requireActive("en", LanguageCapability.ADVERT_CONTENT);
        assertEquals("en", base.code());
        assertNotNull(base);
        assertNotEquals(regional.code(), base.code());

        installSnapshot(seedEntity("de", (short) 10, true, true, true));
        assertTrue(service.findByCode("de-at").isEmpty());
        assertFalse(service.supports("de-at", LanguageCapability.ADVERT_CONTENT));
        assertThrows(
                UnsupportedLanguageException.class,
                () -> service.requireActive("de-at", LanguageCapability.ADVERT_CONTENT));
    }

    @Test
    void invalidSyntaxThrowsInvalidLanguageCodeException() {
        assertThrows(InvalidLanguageCodeException.class, () -> service.canonicalizeCode("e"));
        assertThrows(InvalidLanguageCodeException.class, () -> service.findByCode("en--gb"));
        assertFalse(service.isValidSyntax("abcd"));
        assertFalse(service.supports("en--gb", LanguageCapability.UI_MESSAGE));
    }

    @Test
    void missingLanguageThrowsUnsupportedLanguageException() {
        installSnapshot(seed("de", (short) 10, true, true, true));

        assertTrue(service.findByCode("fr").isEmpty());
        assertThrows(
                UnsupportedLanguageException.class,
                () -> service.requireActive("fr", LanguageCapability.ADVERT_CONTENT));
    }

    @Test
    void inactiveLanguageRejected() {
        installSnapshot(seed("xx", (short) 5, false, true, true));

        assertThrows(
                UnsupportedLanguageException.class,
                () -> service.requireActive("xx", LanguageCapability.UI_MESSAGE));
        assertFalse(service.supports("xx", LanguageCapability.UI_MESSAGE));
    }

    @Test
    void unsupportedUiCapabilityRejected() {
        installSnapshot(language("de", (short) 10, true, false, true));

        assertThrows(
                UnsupportedLanguageException.class,
                () -> service.requireActive("de", LanguageCapability.UI_MESSAGE));
        assertTrue(service.supports("de", LanguageCapability.ADVERT_CONTENT));
        assertFalse(service.supports("de", LanguageCapability.UI_MESSAGE));
    }

    @Test
    void unsupportedContentCapabilityRejected() {
        installSnapshot(language("tr", (short) 20, true, true, false));

        assertThrows(
                UnsupportedLanguageException.class,
                () -> service.requireActive("tr", LanguageCapability.ADVERT_CONTENT));
        assertTrue(service.supports("tr", LanguageCapability.UI_MESSAGE));
        assertFalse(service.supports("tr", LanguageCapability.ADVERT_CONTENT));
    }

    @Test
    void listActiveFiltersAndPreservesOrder() {
        installSnapshot(
                language("de", (short) 10, true, true, true),
                language("tr", (short) 20, true, true, false),
                language("en", (short) 30, false, true, true),
                language("fr", (short) 40, true, false, true));

        List<LanguageDescriptor> uiActive = service.listActive(LanguageCapability.UI_MESSAGE);
        assertEquals(List.of("de", "tr"), uiActive.stream().map(LanguageDescriptor::code).toList());

        List<LanguageDescriptor> contentActive = service.listActive(LanguageCapability.ADVERT_CONTENT);
        assertEquals(List.of("de", "fr"), contentActive.stream().map(LanguageDescriptor::code).toList());
    }

    @Test
    void successfulReloadReplacesSnapshot() {
        LanguageRegistrySnapshot first = snapshotFrom(seed("de", (short) 10, true, true, true));
        LanguageRegistrySnapshot second = snapshotFrom(seed("tr", (short) 20, true, true, true));

        when(snapshotLoader.loadSnapshot()).thenReturn(first).thenReturn(second);

        service.reloadSnapshot();
        assertEquals("de", service.currentSnapshot().languages().get(0).code());

        service.reloadSnapshot();
        assertEquals("tr", service.currentSnapshot().languages().get(0).code());
    }

    @Test
    void failedReloadPreservesPreviousSnapshot() {
        LanguageRegistrySnapshot first = snapshotFrom(seed("de", (short) 10, true, true, true));
        when(snapshotLoader.loadSnapshot()).thenReturn(first).thenThrow(new RuntimeException("db down"));

        service.reloadSnapshot();
        LanguageRegistrySnapshot afterFirstReload = service.currentSnapshot();

        RuntimeException failure =
                assertThrows(RuntimeException.class, () -> service.reloadSnapshot());
        assertEquals("db down", failure.getMessage());
        assertEquals(afterFirstReload, service.currentSnapshot());
        assertEquals("de", service.currentSnapshot().languages().get(0).code());
    }

    @Test
    void emptyReloadDoesNotPublishEmptySnapshotAndPreservesPrevious() {
        LanguageRegistrySnapshotLoader realLoader = new LanguageRegistrySnapshotLoader(languageRepository);
        LanguageRegistryServiceImpl wiredService = new LanguageRegistryServiceImpl(realLoader);
        when(languageRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(seed("xx", (short) 5, true, true, true)));
        wiredService.reloadSnapshot();
        LanguageRegistrySnapshot afterSuccessfulLoad = wiredService.currentSnapshot();
        assertFalse(afterSuccessfulLoad.languages().isEmpty());

        when(languageRepository.findAll(any(Sort.class))).thenReturn(List.of());
        IllegalStateException failure =
                assertThrows(IllegalStateException.class, wiredService::reloadSnapshot);
        assertEquals(
                "Language registry snapshot must not be empty: no language rows were loaded from the database",
                failure.getMessage());
        assertEquals(afterSuccessfulLoad, wiredService.currentSnapshot());
        assertTrue(wiredService.currentSnapshot().languages().stream().anyMatch(d -> "xx".equals(d.code())));
    }

    @Test
    void failedBuilderPreservesPreviousSnapshot() {
        installSnapshot(seed("de", (short) 10, true, true, true));
        LanguageRegistrySnapshot beforeFailedReload = service.currentSnapshot();
        when(snapshotLoader.loadSnapshot())
                .thenThrow(new IllegalArgumentException("Duplicate language code: de"));

        assertThrows(IllegalArgumentException.class, () -> service.reloadSnapshot());
        assertEquals(beforeFailedReload, service.currentSnapshot());
        assertEquals("de", service.currentSnapshot().languages().get(0).code());
    }

    @Test
    void readOperationsDoNotInvokeLoader() {
        installSnapshot(seed("en", (short) 30, true, true, true));
        reset(snapshotLoader);

        service.findByCode("en");
        service.supports("en", LanguageCapability.ADVERT_CONTENT);
        service.listActive(LanguageCapability.UI_MESSAGE);
        service.requireActive("en", LanguageCapability.UI_MESSAGE);
        service.isValidSyntax("en");
        service.canonicalizeCode("EN");

        verify(snapshotLoader, never()).loadSnapshot();
        verifyNoInteractions(languageRepository);
    }

    private void installSnapshot(Language... languages) {
        installSnapshot(List.of(languages));
    }

    private void installSnapshot(List<Language> languages) {
        when(snapshotLoader.loadSnapshot()).thenReturn(LanguageRegistrySnapshotBuilder.buildFromEntities(languages));
        service.reloadSnapshot();
    }

    private static LanguageRegistrySnapshot snapshotFrom(Language... languages) {
        return LanguageRegistrySnapshotBuilder.buildFromEntities(List.of(languages));
    }

    private static Language seed(
            String code, short sortOrder, boolean active, boolean ui, boolean content) {
        return language(code, sortOrder, active, ui, content);
    }

    private static Language seedEntity(
            String code, short sortOrder, boolean active, boolean ui, boolean content) {
        return language(code, sortOrder, active, ui, content);
    }

    private static Language language(
            String code, short sortOrder, boolean active, boolean ui, boolean content) {
        return Language.builder()
                .code(code)
                .name(code)
                .nativeName(code)
                .active(active)
                .uiSupported(ui)
                .contentSupported(content)
                .textDirection(TextDirection.LTR)
                .sortOrder(sortOrder)
                .build();
    }
}