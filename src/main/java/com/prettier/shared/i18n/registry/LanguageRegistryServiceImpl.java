package com.prettier.shared.i18n.registry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class LanguageRegistryServiceImpl implements LanguageRegistryService {

    private final LanguageRegistrySnapshotLoader snapshotLoader;
    private final AtomicReference<LanguageRegistrySnapshot> snapshotRef;

    public LanguageRegistryServiceImpl(LanguageRegistrySnapshotLoader snapshotLoader) {
        this.snapshotLoader = snapshotLoader;
        this.snapshotRef = new AtomicReference<>(initialEmptySnapshot());
    }

    @Override
    public String canonicalizeCode(String raw) {
        return LanguageCodeCanonicalizer.canonicalize(raw);
    }

    @Override
    public boolean isValidSyntax(String code) {
        if (code == null) {
            return false;
        }
        try {
            LanguageCodeCanonicalizer.canonicalize(code);
            return true;
        } catch (InvalidLanguageCodeException ex) {
            return false;
        }
    }

    @Override
    public Optional<LanguageDescriptor> findByCode(String code) {
        String canonical = LanguageCodeCanonicalizer.canonicalize(code);
        return snapshotRef.get().findByCode(canonical);
    }

    @Override
    public LanguageDescriptor requireActive(String code, LanguageCapability capability) {
        String canonical = LanguageCodeCanonicalizer.canonicalize(code);
        LanguageDescriptor descriptor =
                snapshotRef
                        .get()
                        .findByCode(canonical)
                        .orElseThrow(
                                () ->
                                        new UnsupportedLanguageException(
                                                "Language is not registered: " + canonical));

        if (!descriptor.active()) {
            throw new UnsupportedLanguageException("Language is not active: " + canonical);
        }

        if (!supportsCapability(descriptor, capability)) {
            throw new UnsupportedLanguageException(
                    "Language does not support capability " + capability + ": " + canonical);
        }

        return descriptor;
    }

    @Override
    public boolean supports(String code, LanguageCapability capability) {
        if (!isValidSyntax(code)) {
            return false;
        }
        String canonical = LanguageCodeCanonicalizer.canonicalize(code);
        return snapshotRef
                .get()
                .findByCode(canonical)
                .filter(LanguageDescriptor::active)
                .filter(descriptor -> supportsCapability(descriptor, capability))
                .isPresent();
    }

    @Override
    public List<LanguageDescriptor> listActive(LanguageCapability capability) {
        List<LanguageDescriptor> result = new ArrayList<>();
        for (LanguageDescriptor descriptor : snapshotRef.get().languages()) {
            if (descriptor.active() && supportsCapability(descriptor, capability)) {
                result.add(descriptor);
            }
        }
        return List.copyOf(result);
    }

    @Override
    public LanguageRegistrySnapshot currentSnapshot() {
        return snapshotRef.get();
    }

    @Override
    public void reloadSnapshot() {
        try {
            LanguageRegistrySnapshot loaded = snapshotLoader.loadSnapshot();
            snapshotRef.set(loaded);
        } catch (RuntimeException ex) {
            log.warn("Language registry snapshot reload failed; keeping previous snapshot", ex);
            throw ex;
        }
    }

    private static boolean supportsCapability(LanguageDescriptor descriptor, LanguageCapability capability) {
        return switch (capability) {
            case UI_MESSAGE -> descriptor.uiSupported();
            case ADVERT_CONTENT -> descriptor.contentSupported();
        };
    }

    private static LanguageRegistrySnapshot initialEmptySnapshot() {
        return LanguageRegistrySnapshotBuilder.buildFromEntities(List.of());
    }
}