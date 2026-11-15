package com.prettier.shared.i18n.registry;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import com.prettier.repository.LanguageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Sort;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LanguageRegistryInitializerTest {

    private static final AtomicReference<LanguageRegistryService> SERVICE_OVERRIDE =
            new AtomicReference<>();

    @AfterEach
    void clearServiceOverride() {
        SERVICE_OVERRIDE.set(null);
    }

    @Test
    void applicationReadyTriggersExactlyOneInitialReload() {
        LanguageRegistryService service = mock(LanguageRegistryService.class);
        SERVICE_OVERRIDE.set(service);

        try (ConfigurableApplicationContext context =
                new SpringApplication(MockRegistryBootConfig.class)
                        .run("--spring.main.web-application-type=none")) {
            assertThat(context).isNotNull();
            verify(service, times(1)).reloadSnapshot();
        }
    }

    @Test
    void successfulStartupPublishesLoaderBackedSnapshot() {
        LanguageRegistrySnapshotLoader loader = mock(LanguageRegistrySnapshotLoader.class);
        LanguageRegistrySnapshot loaded =
                LanguageRegistrySnapshotBuilder.buildFromEntities(
                        List.of(seed("de", (short) 10), seed("tr", (short) 20), seed("en", (short) 30)));
        when(loader.loadSnapshot()).thenReturn(loaded);
        LoaderBackedBootConfig.registeredLoader.set(loader);

        try (ConfigurableApplicationContext context =
                new SpringApplication(LoaderBackedBootConfig.class)
                        .run("--spring.main.web-application-type=none")) {
            LanguageRegistryService registry = context.getBean(LanguageRegistryService.class);
            assertThat(registry.currentSnapshot().languages()).hasSize(3);
            assertThat(registry.findByCode("de")).isPresent();
        } finally {
            LoaderBackedBootConfig.registeredLoader.set(null);
        }
    }

    @Test
    void failedReloadPropagatesOriginalException() {
        LanguageRegistryService service = mock(LanguageRegistryService.class);
        RuntimeException failure = new IllegalStateException("registry load failed");
        doThrow(failure).when(service).reloadSnapshot();
        SERVICE_OVERRIDE.set(service);

        new ApplicationContextRunner()
                .withUserConfiguration(MockRegistryBootConfig.class)
                .run(
                        context -> {
                            ApplicationReadyEvent readyEvent =
                                    new ApplicationReadyEvent(
                                            new SpringApplication(),
                                            new String[0],
                                            context,
                                            Duration.ZERO);
                            assertThatThrownBy(() -> context.publishEvent(readyEvent))
                                    .isSameAs(failure);
                        });
    }

    @Test
    void emptyInitialRegistryPreventsSuccessfulStartup() {
        LanguageRepository repository = mock(LanguageRepository.class);
        when(repository.findAll(any(Sort.class))).thenReturn(List.of());
        EmptyRegistryBootConfig.registeredRepository.set(repository);

        try {
            assertThatThrownBy(
                            () ->
                                    new SpringApplication(EmptyRegistryBootConfig.class)
                                            .run("--spring.main.web-application-type=none"))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage(
                            "Language registry snapshot must not be empty: no language rows were loaded from the database");
        } finally {
            EmptyRegistryBootConfig.registeredRepository.set(null);
        }
    }

    @Test
    void failedInitialReloadPreventsSuccessfulStartup() {
        LanguageRegistryService service = mock(LanguageRegistryService.class);
        doThrow(new IllegalStateException("registry load failed")).when(service).reloadSnapshot();
        SERVICE_OVERRIDE.set(service);

        assertThatThrownBy(
                        () ->
                                new SpringApplication(MockRegistryBootConfig.class)
                                        .run("--spring.main.web-application-type=none"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("registry load failed");
    }

    @Test
    void unrelatedEventsDoNotTriggerReload() {
        LanguageRegistryService service = mock(LanguageRegistryService.class);
        SERVICE_OVERRIDE.set(service);

        new ApplicationContextRunner()
                .withUserConfiguration(MockRegistryBootConfig.class)
                .run(
                        context -> {
                            verify(service, never()).reloadSnapshot();
                            context.publishEvent(new ContextRefreshedEvent(context));
                            verify(service, never()).reloadSnapshot();
                        });
    }

    @Test
    void minimalBootContextStartsWhenReloadSucceeds() {
        LanguageRegistryService service = mock(LanguageRegistryService.class);
        SERVICE_OVERRIDE.set(service);

        try (ConfigurableApplicationContext context =
                new SpringApplication(MockRegistryBootConfig.class)
                        .run("--spring.main.web-application-type=none")) {
            assertThat(context.getBean(LanguageRegistryInitializer.class)).isNotNull();
            verify(service, times(1)).reloadSnapshot();
        }
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

    @SpringBootConfiguration
    @EnableAutoConfiguration(
            exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                SecurityAutoConfiguration.class
            })
    @Import(LanguageRegistryInitializer.class)
    static class MockRegistryBootConfig {

        @Bean
        LanguageRegistryService languageRegistryService() {
            LanguageRegistryService override = SERVICE_OVERRIDE.get();
            if (override != null) {
                return override;
            }
            return mock(LanguageRegistryService.class);
        }
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration(
            exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                SecurityAutoConfiguration.class
            })
    @Import({
        LanguageRegistryInitializer.class,
        LanguageRegistryServiceImpl.class,
        LanguageRegistrySnapshotLoader.class
    })
    static class EmptyRegistryBootConfig {

        static final AtomicReference<LanguageRepository> registeredRepository = new AtomicReference<>();

        @Bean
        LanguageRepository languageRepository() {
            LanguageRepository repository = registeredRepository.get();
            if (repository == null) {
                repository = mock(LanguageRepository.class);
                registeredRepository.set(repository);
            }
            return repository;
        }
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration(
            exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class,
                SecurityAutoConfiguration.class
            })
    @Import({LanguageRegistryInitializer.class, LanguageRegistryServiceImpl.class})
    static class LoaderBackedBootConfig {

        static final AtomicReference<LanguageRegistrySnapshotLoader> registeredLoader =
                new AtomicReference<>();

        @Bean
        LanguageRegistrySnapshotLoader languageRegistrySnapshotLoader() {
            LanguageRegistrySnapshotLoader loader = registeredLoader.get();
            if (loader == null) {
                loader = mock(LanguageRegistrySnapshotLoader.class);
                registeredLoader.set(loader);
            }
            return loader;
        }
    }
}