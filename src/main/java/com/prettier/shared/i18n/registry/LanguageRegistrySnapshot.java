package com.prettier.shared.i18n.registry;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable in-memory view of all registry rows (IMPL-LREG-04.2). Active and inactive languages included.
 */
public final class LanguageRegistrySnapshot {

    private final Map<String, LanguageDescriptor> byCode;
    private final List<LanguageDescriptor> languages;

    LanguageRegistrySnapshot(List<LanguageDescriptor> orderedDescriptors) {
        Objects.requireNonNull(orderedDescriptors, "orderedDescriptors must not be null");
        this.languages = List.copyOf(orderedDescriptors);

        Map<String, LanguageDescriptor> codeIndex = new java.util.LinkedHashMap<>();
        for (LanguageDescriptor descriptor : orderedDescriptors) {
            String code = Objects.requireNonNull(descriptor.code(), "descriptor code must not be null");
            if (codeIndex.containsKey(code)) {
                throw new IllegalArgumentException("Duplicate language code: " + code);
            }
            codeIndex.put(code, descriptor);
        }
        this.byCode = Map.copyOf(codeIndex);
    }

    public Optional<LanguageDescriptor> findByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(byCode.get(code));
    }

    /**
     * All languages in deterministic {@code sortOrder}, then {@code code} order. Immutable.
     */
    public List<LanguageDescriptor> languages() {
        return languages;
    }
}