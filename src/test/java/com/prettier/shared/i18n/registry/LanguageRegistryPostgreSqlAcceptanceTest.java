package com.prettier.shared.i18n.registry;

import com.prettier.RealEstateApplication;
import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import com.prettier.repository.LanguageRepository;
import com.prettier.repository.RoleRepository;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PostgreSQL-backed acceptance tests for Language Registry M1 (IMPL-LREG-04.5).
 * Database mutations run in a test-managed transaction that rolls back after each test.
 */
@SpringBootTest(classes = RealEstateApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class LanguageRegistryPostgreSqlAcceptanceTest {

    private static final String TEMP_CHILD = "en-gb";
    private static final String TEMP_NO_UI = "xx-no-ui-acc";
    private static final String TEMP_NO_CONTENT = "xx-no-content-acc";

    @DynamicPropertySource
    static void registerDataSource(DynamicPropertyRegistry registry) {
        String url =
                System.getenv()
                        .getOrDefault(
                                "SPRING_DATASOURCE_URL",
                                "jdbc:postgresql://localhost:5433/real_estate_db");
        String username = System.getenv().getOrDefault("SPRING_DATASOURCE_USERNAME", "db_user");
        String password = System.getenv().getOrDefault("SPRING_DATASOURCE_PASSWORD", "db_password");
        registry.add("spring.datasource.url", () -> url);
        registry.add("spring.datasource.username", () -> username);
        registry.add("spring.datasource.password", () -> password);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
    }

    @Autowired private Flyway flyway;
    @Autowired private LanguageRepository languageRepository;
    @Autowired private LanguageRegistrySnapshotLoader snapshotLoader;
    @Autowired private LanguageRegistryService languageRegistryService;
    @Autowired private RoleRepository roleRepository;

    /**
     * Ends the test transaction (rollback) before Spring's default teardown, then reloads the in-memory registry from
     * committed data so later tests and developers do not see temporary rows.
     */
    @AfterEach
    void rollbackTestTransactionAndRefreshRegistrySnapshot() {
        if (TestTransaction.isActive()) {
            TestTransaction.flagForRollback();
            TestTransaction.end();
        }
        languageRegistryService.reloadSnapshot();
    }

    @Test
    void flywayMigrationsApplySuccessfullyIncludingLanguagesRegistry() {
        MigrationInfo[] migrations = flyway.info().all();
        assertTrue(migrations.length >= 3, "expected at least V1, V2 and V3");
        assertTrue(
                Arrays.stream(migrations)
                        .filter(m -> m.getVersion() != null)
                        .allMatch(m -> MigrationState.SUCCESS == m.getState()),
                "every versioned migration must be applied successfully");
        assertTrue(
                Arrays.stream(migrations)
                        .anyMatch(
                                m -> m.getVersion() != null && "3".equals(m.getVersion().toString())),
                "V3 languages registry migration must be applied");
    }

    @Test
    void repositoryReturnsSeededLanguagesWithDeterministicOrdering() {
        List<Language> ordered =
                languageRepository.findAll(
                        Sort.by(Sort.Order.asc("sortOrder"), Sort.Order.asc("code")));

        assertTrue(ordered.size() >= 3);
        assertEquals("de", ordered.get(0).getCode());
        assertEquals("tr", ordered.get(1).getCode());
        assertEquals("en", ordered.get(2).getCode());
        assertNull(ordered.get(0).getParent());
    }

    @Test
    void repositoryResolvesParentRelationshipForRegionalLanguage() {
        Language parent = languageRepository.findById("en").orElseThrow();
        languageRepository.saveAndFlush(
                Language.builder()
                        .code(TEMP_CHILD)
                        .parent(parent)
                        .name("English (United Kingdom)")
                        .nativeName("English")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 35)
                        .build());

        Language loaded = languageRepository.findById(TEMP_CHILD).orElseThrow();
        assertNotNull(loaded.getParent());
        assertEquals("en", loaded.getParent().getCode());
    }

    @Test
    void snapshotLoaderMapsPostgreSqlRowsToDescriptorsWithParentCode() {
        Language parent = languageRepository.findById("en").orElseThrow();
        languageRepository.saveAndFlush(
                Language.builder()
                        .code(TEMP_CHILD)
                        .parent(parent)
                        .name("English (United Kingdom)")
                        .nativeName("English")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 35)
                        .build());

        LanguageRegistrySnapshot snapshot = snapshotLoader.loadSnapshot();
        Optional<LanguageDescriptor> regional = snapshot.findByCode(TEMP_CHILD);
        assertTrue(regional.isPresent());
        assertEquals("en", regional.get().parentCode());
        assertTrue(snapshot.findByCode("de").isPresent());
    }

    @Test
    void snapshotLoaderRejectsEmptyRepositoryResult() {
        languageRepository.deleteAll();
        languageRepository.flush();

        IllegalStateException failure =
                assertThrows(IllegalStateException.class, snapshotLoader::loadSnapshot);
        assertEquals(
                "Language registry snapshot must not be empty: no language rows were loaded from the database",
                failure.getMessage());
    }

    @Test
    void serviceSnapshotInitializedFromPostgreSqlOnApplicationReady() {
        LanguageRegistrySnapshot snapshot = languageRegistryService.currentSnapshot();
        assertFalse(snapshot.languages().isEmpty());
        assertTrue(snapshot.findByCode("de").isPresent());
        assertTrue(snapshot.findByCode("tr").isPresent());
        assertTrue(snapshot.findByCode("en").isPresent());
        assertEquals("German", snapshot.findByCode("de").orElseThrow().name());
        assertFalse(snapshot.findByCode(TEMP_NO_UI).isPresent());
        assertFalse(snapshot.findByCode(TEMP_CHILD).isPresent());
    }

    @Test
    void serviceCanonicalizedLookupAndExactRegionalLookupWithoutParentFallback() {
        Language parent = languageRepository.findById("en").orElseThrow();
        languageRepository.saveAndFlush(
                Language.builder()
                        .code(TEMP_CHILD)
                        .parent(parent)
                        .name("English (United Kingdom)")
                        .nativeName("English")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 35)
                        .build());

        languageRegistryService.reloadSnapshot();

        assertTrue(languageRegistryService.findByCode("EN-GB").isPresent());
        assertEquals(
                "en",
                languageRegistryService.findByCode("EN-GB").orElseThrow().parentCode());
        assertTrue(languageRegistryService.findByCode("en").isPresent());
        assertEquals("en", languageRegistryService.findByCode("en").orElseThrow().code());
        assertFalse(
                languageRegistryService
                        .findByCode("en")
                        .map(LanguageDescriptor::code)
                        .filter(TEMP_CHILD::equals)
                        .isPresent());
    }

    @Test
    void serviceCapabilitiesFollowDatabaseFlags() {
        languageRepository.save(
                Language.builder()
                        .code(TEMP_NO_UI)
                        .name("No UI")
                        .nativeName("No UI")
                        .active(true)
                        .uiSupported(false)
                        .contentSupported(true)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 905)
                        .build());
        languageRepository.save(
                Language.builder()
                        .code(TEMP_NO_CONTENT)
                        .name("No Content")
                        .nativeName("No Content")
                        .active(true)
                        .uiSupported(true)
                        .contentSupported(false)
                        .textDirection(TextDirection.LTR)
                        .sortOrder((short) 906)
                        .build());
        languageRepository.flush();

        languageRegistryService.reloadSnapshot();

        var uiActive =
                languageRegistryService.listActive(LanguageCapability.UI_MESSAGE).stream()
                        .map(LanguageDescriptor::code)
                        .toList();
        var contentActive =
                languageRegistryService.listActive(LanguageCapability.ADVERT_CONTENT).stream()
                        .map(LanguageDescriptor::code)
                        .toList();

        assertTrue(uiActive.contains("de"));
        assertFalse(uiActive.contains(TEMP_NO_UI));
        assertTrue(contentActive.contains("tr"));
        assertFalse(contentActive.contains(TEMP_NO_CONTENT));
        assertTrue(languageRegistryService.supports(TEMP_NO_UI, LanguageCapability.ADVERT_CONTENT));
        assertFalse(languageRegistryService.supports(TEMP_NO_UI, LanguageCapability.UI_MESSAGE));
    }

    @Test
    void dataInitializerRemainsCompatibleAfterRegistryStartup() {
        assertTrue(roleRepository.count() >= 1, "DataInitializer should seed roles on first startup");
    }
}