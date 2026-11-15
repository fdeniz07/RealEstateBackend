package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Maps {@link Language} rows to {@link LanguageRegistrySnapshot} (IMPL-LREG-04.2). No fallback lookup.
 */
public final class LanguageRegistrySnapshotBuilder {

    private static final Comparator<LanguageDescriptor> DESCRIPTOR_ORDER =
            Comparator.comparing(LanguageDescriptor::sortOrder).thenComparing(LanguageDescriptor::code);

    private LanguageRegistrySnapshotBuilder() {}

    public static LanguageRegistrySnapshot buildFromEntities(Collection<Language> entities) {
        if (entities == null) {
            throw new IllegalArgumentException("Language entities must not be null");
        }

        List<LanguageDescriptor> descriptors = new ArrayList<>(entities.size());
        for (Language entity : entities) {
            if (entity == null) {
                throw new IllegalArgumentException("Language entity collection must not contain null elements");
            }
            descriptors.add(toDescriptor(entity));
        }

        descriptors.sort(DESCRIPTOR_ORDER);

        Set<String> seenCodes = new HashSet<>();
        for (LanguageDescriptor descriptor : descriptors) {
            if (!seenCodes.add(descriptor.code())) {
                throw new IllegalArgumentException("Duplicate language code: " + descriptor.code());
            }
        }

        return new LanguageRegistrySnapshot(descriptors);
    }

    static LanguageDescriptor toDescriptor(Language entity) {
        String code = Objects.requireNonNull(entity.getCode(), "Language code must not be null");
        String parentCode = null;
        Language parent = entity.getParent();
        if (parent != null) {
            parentCode = Objects.requireNonNull(parent.getCode(), "Parent language code must not be null");
        }

        TextDirection textDirection =
                Objects.requireNonNull(entity.getTextDirection(), "textDirection must not be null");

        return new LanguageDescriptor(
                code,
                parentCode,
                entity.getName(),
                entity.getNativeName(),
                entity.isActive(),
                entity.isUiSupported(),
                entity.isContentSupported(),
                textDirection,
                entity.getSortOrder());
    }
}