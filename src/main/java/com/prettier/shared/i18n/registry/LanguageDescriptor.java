package com.prettier.shared.i18n.registry;

import com.prettier.entity.enums.TextDirection;

/**
 * Immutable registry row view for in-memory snapshot (IMPL-LREG-04.1). No JPA types.
 */
public record LanguageDescriptor(
        String code,
        String parentCode,
        String name,
        String nativeName,
        boolean active,
        boolean uiSupported,
        boolean contentSupported,
        TextDirection textDirection,
        short sortOrder) {}