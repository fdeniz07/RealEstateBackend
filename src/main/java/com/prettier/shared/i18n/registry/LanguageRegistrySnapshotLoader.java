package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Loads {@link Language} rows inside a read-only transaction so lazy {@code parent} can be resolved (IMPL-LREG-04.3).
 */
@Component
@RequiredArgsConstructor
public class LanguageRegistrySnapshotLoader {

    private static final Sort LOAD_ORDER =
            Sort.by(Sort.Order.asc("sortOrder"), Sort.Order.asc("code"));

    private final LanguageRepository languageRepository;

    @Transactional(readOnly = true)
    public LanguageRegistrySnapshot loadSnapshot() {
        List<Language> entities = languageRepository.findAll(LOAD_ORDER);
        LanguageRegistrySnapshot snapshot = LanguageRegistrySnapshotBuilder.buildFromEntities(entities);
        if (snapshot.languages().isEmpty()) {
            throw new IllegalStateException(
                    "Language registry snapshot must not be empty: no language rows were loaded from the database");
        }
        return snapshot;
    }
}