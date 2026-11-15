package com.prettier.shared.i18n.registry;

import java.util.List;
import java.util.Optional;

public interface LanguageRegistryService {

    String canonicalizeCode(String raw);

    boolean isValidSyntax(String code);

    Optional<LanguageDescriptor> findByCode(String code);

    LanguageDescriptor requireActive(String code, LanguageCapability capability);

    boolean supports(String code, LanguageCapability capability);

    List<LanguageDescriptor> listActive(LanguageCapability capability);

    LanguageRegistrySnapshot currentSnapshot();

    void reloadSnapshot();
}