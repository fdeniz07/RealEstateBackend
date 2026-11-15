package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import com.prettier.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageRegistrySnapshotLoaderTest {

    @Mock private LanguageRepository languageRepository;

    @Test
    void nonEmptyRepositoryProducesLoadableSnapshot() {
        LanguageRegistrySnapshotLoader loader = new LanguageRegistrySnapshotLoader(languageRepository);
        when(languageRepository.findAll(any(Sort.class)))
                .thenReturn(List.of(seed("aa", (short) 1)));

        LanguageRegistrySnapshot snapshot = loader.loadSnapshot();

        assertFalse(snapshot.languages().isEmpty());
        assertEquals("aa", snapshot.languages().get(0).code());
    }

    @Test
    void emptyRepositoryResultFailsInitialization() {
        LanguageRegistrySnapshotLoader loader = new LanguageRegistrySnapshotLoader(languageRepository);
        when(languageRepository.findAll(any(Sort.class))).thenReturn(List.of());

        IllegalStateException failure =
                assertThrows(IllegalStateException.class, loader::loadSnapshot);
        assertEquals(
                "Language registry snapshot must not be empty: no language rows were loaded from the database",
                failure.getMessage());
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