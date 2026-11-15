package com.prettier.shared.i18n.registry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Loads the first database-backed language registry snapshot after the application is ready (IMPL-LREG-04.4).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LanguageRegistryInitializer {

    private final LanguageRegistryService languageRegistryService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        log.info("Loading language registry snapshot (ApplicationReadyEvent)");
        languageRegistryService.reloadSnapshot();
    }
}