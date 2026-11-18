package com.prettier.entity.concretes;

import com.prettier.RealEstateApplication;
import com.prettier.entity.enums.AdvertStatus;
import com.prettier.entity.enums.TranslationPublicationStatus;
import com.prettier.repository.AdvertRepository;
import com.prettier.repository.AdvertTranslationRepository;
import com.prettier.repository.AdvertTypeRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.repository.CityRepository;
import com.prettier.repository.CountryRepository;
import com.prettier.repository.DistrictRepository;
import com.prettier.repository.LanguageRepository;
import com.prettier.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.prettier.test.support.PostgresIntegrationTestSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * PostgreSQL-backed persistence tests for ARCH-3B {@code advert_translations} (DEC-007).
 */
@SpringBootTest(classes = RealEstateApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AdvertTranslationPostgreSqlAcceptanceTest extends PostgresIntegrationTestSupport {

    @Autowired private Flyway flyway;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private LanguageRepository languageRepository;
    @Autowired private AdvertRepository advertRepository;
    @Autowired private AdvertTranslationRepository advertTranslationRepository;
    @Autowired private AdvertTypeRepository advertTypeRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CountryRepository countryRepository;
    @Autowired private CityRepository cityRepository;
    @Autowired private DistrictRepository districtRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void flywayV4MigrationAppliesSuccessfullyAfterPriorVersions() {
        MigrationInfo[] migrations = flyway.info().all();
        assertTrue(migrations.length >= 4, "expected at least V1 through V4");
        assertTrue(
                Arrays.stream(migrations)
                        .filter(m -> m.getVersion() != null)
                        .allMatch(m -> MigrationState.SUCCESS == m.getState()),
                "every versioned migration must be applied successfully");
        assertTrue(
                Arrays.stream(migrations)
                        .anyMatch(m -> m.getVersion() != null && "4".equals(m.getVersion().toString())),
                "V4 advert_translations migration must be applied");
    }

    @Test
    void sourceLanguageCodeAcceptsNullOnAdvert() {
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("null-src-advert"));
        advertRepository.saveAndFlush(advert);

        assertNull(advert.getSourceLanguage());

        Object dbValue =
                jdbcTemplate.queryForObject(
                        "SELECT source_language_code FROM adverts WHERE id = ?",
                        Object.class,
                        advert.getId());
        assertNull(dbValue);
    }

    @Test
    void validSourceLanguageForeignKeySucceeds() {
        Language german = languageRepository.findById("de").orElseThrow();
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("with-src-lang"));
        advert.setSourceLanguage(german);
        advertRepository.saveAndFlush(advert);

        assertNotNull(advert.getSourceLanguage());
        assertThat(advert.getSourceLanguage().getCode()).isEqualTo("de");
    }

    @Test
    void invalidSourceLanguageForeignKeyFails() {
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("invalid-src-fk"));
        Long advertId = advert.getId();
        assertThrows(
                DataAccessException.class,
                () ->
                        jdbcTemplate.update(
                                "UPDATE adverts SET source_language_code = ? WHERE id = ?",
                                "xx-invalid-src",
                                advertId));
    }

    @Test
    void validAdvertTranslationForeignKeysPersist() {
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("tr-fk-ok"));
        Language turkish = languageRepository.findById("tr").orElseThrow();

        AdvertTranslation translation =
                AdvertTranslation.builder()
                        .advert(advert)
                        .language(turkish)
                        .title("Baslik yeterli")
                        .desc("Aciklama")
                        .slug(uniqueSlug("tr-slug"))
                        .translationPublicationStatus(TranslationPublicationStatus.DRAFT)
                        .build();

        advertTranslationRepository.saveAndFlush(translation);

        assertNotNull(translation.getId());
        assertThat(translation.getLanguage().getCode()).isEqualTo("tr");
        assertThat(translation.getTranslationPublicationStatus())
                .isEqualTo(TranslationPublicationStatus.DRAFT);
    }

    @Test
    void invalidAdvertForeignKeyFails() {
        assertThrows(
                DataAccessException.class,
                () ->
                        jdbcTemplate.update(
                                """
                                INSERT INTO advert_translations
                                (advert_id, language_code, title, slug, translation_publication_status, create_at)
                                VALUES (?, 'de', 'Valid title', 'invalid-advert-fk', 'DRAFT', CURRENT_TIMESTAMP)
                                """,
                                -1L));
    }

    @Test
    void invalidLanguageForeignKeyFails() {
        Long advertId = createMinimalLegacyAdvert(uniqueSlug("invalid-lang-fk")).getId();
        assertThrows(
                DataAccessException.class,
                () ->
                        jdbcTemplate.update(
                                """
                                INSERT INTO advert_translations
                                (advert_id, language_code, title, slug, translation_publication_status, create_at)
                                VALUES (?, 'xx-invalid', 'Valid title', 'invalid-lang-fk', 'DRAFT', CURRENT_TIMESTAMP)
                                """,
                                advertId));
    }

    @Test
    void duplicateAdvertIdAndLanguageCodeFails() {
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("dup-lang-pair"));
        Language english = languageRepository.findById("en").orElseThrow();

        advertTranslationRepository.saveAndFlush(
                AdvertTranslation.builder()
                        .advert(advert)
                        .language(english)
                        .title("First title here")
                        .slug(uniqueSlug("en-slug-1"))
                        .translationPublicationStatus(TranslationPublicationStatus.DRAFT)
                        .build());

        assertThrows(
                DataIntegrityViolationException.class,
                () ->
                        advertTranslationRepository.saveAndFlush(
                                AdvertTranslation.builder()
                                        .advert(advert)
                                        .language(english)
                                        .title("Second title")
                                        .slug(uniqueSlug("en-slug-2"))
                                        .translationPublicationStatus(TranslationPublicationStatus.IN_REVIEW)
                                        .build()));
    }

    @Test
    void sameSlugInSameLanguageAcrossDifferentAdvertsFailsOnTranslationTable() {
        Language german = languageRepository.findById("de").orElseThrow();
        String sharedTranslationSlug = uniqueSlug("shared-de-slug");

        Advert firstAdvert = createMinimalLegacyAdvert(uniqueSlug("legacy-a-" + sharedTranslationSlug));
        advertTranslationRepository.saveAndFlush(
                AdvertTranslation.builder()
                        .advert(firstAdvert)
                        .language(german)
                        .title("Erste Anzeige")
                        .slug(sharedTranslationSlug)
                        .translationPublicationStatus(TranslationPublicationStatus.DRAFT)
                        .build());

        Advert secondAdvert = createMinimalLegacyAdvert(uniqueSlug("legacy-b-" + sharedTranslationSlug));

        assertThrows(
                DataIntegrityViolationException.class,
                () ->
                        advertTranslationRepository.saveAndFlush(
                                AdvertTranslation.builder()
                                        .advert(secondAdvert)
                                        .language(german)
                                        .title("Zweite Anzeige")
                                        .slug(sharedTranslationSlug)
                                        .translationPublicationStatus(TranslationPublicationStatus.DRAFT)
                                        .build()));
    }

    @Test
    void sameSlugInDifferentLanguagesSucceedsOnTranslationTable() {
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("multi-lang-slug"));
        String sharedSlug = uniqueSlug("cross-lang");

        advertTranslationRepository.saveAndFlush(
                AdvertTranslation.builder()
                        .advert(advert)
                        .language(languageRepository.findById("de").orElseThrow())
                        .title("Deutsche Version")
                        .slug(sharedSlug)
                        .translationPublicationStatus(TranslationPublicationStatus.DRAFT)
                        .build());

        AdvertTranslation english =
                AdvertTranslation.builder()
                        .advert(advert)
                        .language(languageRepository.findById("en").orElseThrow())
                        .title("English version")
                        .slug(sharedSlug)
                        .translationPublicationStatus(TranslationPublicationStatus.DRAFT)
                        .build();

        advertTranslationRepository.saveAndFlush(english);
        assertNotNull(english.getId());
    }

    @Test
    void hibernateSchemaValidationIncludesAdvertTranslation() {
        AdvertTranslation loaded =
                entityManager
                        .createQuery("SELECT at FROM AdvertTranslation at", AdvertTranslation.class)
                        .setMaxResults(1)
                        .getResultStream()
                        .findFirst()
                        .orElse(null);
        assertTrue(loaded == null || loaded.getId() != null, "validate mode context started");
    }

    @Test
    void legacyAdvertRowsRemainWithoutSourceLanguageBackfill() {
        Advert advert = createMinimalLegacyAdvert(uniqueSlug("no-backfill"));
        advertRepository.flush();
        assertNull(advertRepository.findById(advert.getId()).orElseThrow().getSourceLanguage());
    }

    @Test
    void languageRegistrySeedEntriesRemainUsableForTranslations() {
        assertThat(languageRepository.findById("de")).isPresent();
        assertThat(languageRepository.findById("tr")).isPresent();
        assertThat(languageRepository.findById("en")).isPresent();

        Advert advert = createMinimalLegacyAdvert(uniqueSlug("registry-seed"));
        advertTranslationRepository.saveAndFlush(
                AdvertTranslation.builder()
                        .advert(advert)
                        .language(languageRepository.findById("tr").orElseThrow())
                        .title("Turkce baslik")
                        .slug(uniqueSlug("registry-tr"))
                        .translationPublicationStatus(TranslationPublicationStatus.PUBLISHED)
                        .build());
    }

    private Advert createMinimalLegacyAdvert(String legacySlug) {
        LocationIds location = requireSeededLocation();
        Country country = countryRepository.findById(location.countryId()).orElseThrow();
        City city = cityRepository.findById(location.cityId()).orElseThrow();
        District district = districtRepository.findById(location.districtId()).orElseThrow();
        User user =
                userRepository
                        .findAll()
                        .stream()
                        .findFirst()
                        .orElseGet(
                                () ->
                                        userRepository
                                                .findByEmail("admin@mail.com")
                                                .orElseThrow(
                                                        () ->
                                                                new IllegalStateException(
                                                                        "No seeded user available for advert fixture")));

        AdvertType advertType =
                advertTypeRepository
                        .save(
                                AdvertType.builder()
                                        .title("Type-" + UUID.randomUUID().toString().substring(0, 8))
                                        .build());

        Category category =
                categoryRepository
                        .save(
                                Category.builder()
                                        .title("Cat-" + UUID.randomUUID().toString().substring(0, 8))
                                        .icon("icon")
                                        .builtIn(false)
                                        .seq(1)
                                        .slug(uniqueSlug("cat"))
                                        .active(true)
                                        .build());

        Advert advert =
                Advert.builder()
                        .title("Title for " + legacySlug)
                        .desc("Description")
                        .slug(legacySlug)
                        .price(100_000.0)
                        .advertStatus(AdvertStatus.PENDING)
                        .builtIn(false)
                        .isActive(true)
                        .viewCount(0)
                        .location("Test location")
                        .advertType(advertType)
                        .country(country)
                        .city(city)
                        .district(district)
                        .user(user)
                        .category(category)
                        .build();

        return advertRepository.save(advert);
    }

    private static String uniqueSlug(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    private LocationIds requireSeededLocation() {
        var rows =
                jdbcTemplate.query(
                        """
                        SELECT c.id AS country_id, ci.id AS city_id, d.id AS district_id
                        FROM countries c
                        JOIN cities ci ON ci.country_id = c.id
                        JOIN districts d ON d.city_id = ci.id
                        WHERE c.code = 'TR'
                        LIMIT 1
                        """,
                        (rs, rowNum) ->
                                new LocationIds(
                                        rs.getLong("country_id"),
                                        rs.getLong("city_id"),
                                        rs.getLong("district_id")));
        assertThat(rows).isNotEmpty();
        return rows.get(0);
    }

    private record LocationIds(long countryId, long cityId, long districtId) {}
}