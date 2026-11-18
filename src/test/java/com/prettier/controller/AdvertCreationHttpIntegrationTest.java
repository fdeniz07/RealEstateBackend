package com.prettier.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prettier.RealEstateApplication;
import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.AdvertType;
import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.User;
import com.prettier.repository.AdvertRepository;
import com.prettier.repository.AdvertTypeRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import com.prettier.test.support.PostgresIntegrationTestSupport;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HTTP-level advert creation tests (B2.1 / B2.1.1) against PostgreSQL ({@code real_estate_test_db}).
 */
@SpringBootTest(
        classes = RealEstateApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdvertCreationHttpIntegrationTest extends PostgresIntegrationTestSupport {

    private static final String LOGIN_PATH = "/api/v1.0/auth/EN/login";
    private static final String SIGN_UP_PATH = "/api/v1.0/auth/EN/signUp";
    private static final String ADVERT_ADD_PATH = "/api/v1.0/adverts/EN/add";
    private static final String BUILTIN_ADMIN_EMAIL = "admin@mail.com";
    private static final String BUILTIN_ADMIN_PASSWORD = "P4ssword";
    private static final String CUSTOMER_PASSWORD = "TestPass1";
    private static final String MALFORMED_JWT = "not.a.valid.jwt";

    @LocalServerPort private int port;

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private AdvertRepository advertRepository;
    @Autowired private AdvertTypeRepository advertTypeRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private UserRepository userRepository;

    private static final ObjectMapper JSON = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    private final List<Long> createdAdvertIds = new ArrayList<>();
    private final List<Long> createdCategoryIds = new ArrayList<>();
    private final List<Long> createdAdvertTypeIds = new ArrayList<>();
    private final List<Long> createdUserIds = new ArrayList<>();

    @AfterEach
    void cleanupFixtures() {
        for (Long id : createdAdvertIds) {
            jdbcTemplate.update("DELETE FROM advert_translations WHERE advert_id = ?", id);
            advertRepository.deleteById(id);
        }
        createdAdvertIds.clear();
        for (Long id : createdCategoryIds) {
            categoryRepository.deleteById(id);
        }
        createdCategoryIds.clear();
        for (Long id : createdAdvertTypeIds) {
            advertTypeRepository.deleteById(id);
        }
        createdAdvertTypeIds.clear();
        for (Long id : createdUserIds) {
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", id);
            userRepository.deleteById(id);
        }
        createdUserIds.clear();
    }

    @Test
    void customerJwt_createsAdvertOwnedByAuthenticatedUser() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        long advertTypeId = requireActiveAdvertTypeId();
        long categoryId = requireActiveCategoryId();
        String title = uniqueTitle();

        Map<String, Object> body = baseCreateBody(title, location, advertTypeId, categoryId);
        HttpResult response = postAdvert(token, body);

        assertThat(response.status()).isEqualTo(HttpStatus.CREATED.value());
        JsonNode root = JSON.readTree(response.body());
        assertThat(root.path("hasError").asBoolean()).isFalse();
        long advertId = root.path("payload").path("id").asLong();
        createdAdvertIds.add(advertId);

        Advert stored = advertRepository.findById(advertId).orElseThrow();
        assertThat(stored.getTitle()).isEqualTo(title);
        assertThat(stored.getSlug()).isNotBlank();
        assertThat(stored.getSourceLanguage()).isNotNull();
        assertThat(stored.getSourceLanguage().getCode()).isEqualTo("de");
        Integer translationCount =
                jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM advert_translations WHERE advert_id = ?",
                        Integer.class,
                        advertId);
        assertThat(translationCount).isEqualTo(1);
        assertThat(response.body()).doesNotContain("passwordHash");
    }

    @Test
    void missingContentLanguage_returnsBadRequest() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        Map<String, Object> body =
                baseCreateBody(
                        uniqueTitle(),
                        location,
                        requireActiveAdvertTypeId(),
                        requireActiveCategoryId());
        body.remove("contentLanguage");
        long countBefore = advertRepository.count();

        HttpResult response = postAdvert(token, body);

        assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void unsupportedContentLanguage_returnsBadRequest() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        Map<String, Object> body =
                baseCreateBody(
                        uniqueTitle(),
                        location,
                        requireActiveAdvertTypeId(),
                        requireActiveCategoryId());
        body.put("contentLanguage", "xx-invalid");
        long countBefore = advertRepository.count();

        HttpResult response = postAdvert(token, body);

        assertThat(response.status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void adminJwt_onAdvertAdd_receivesForbidden() throws Exception {
        String token = login(BUILTIN_ADMIN_EMAIL, BUILTIN_ADMIN_PASSWORD);
        LocationIds location = requireSeededLocation();
        long countBefore = advertRepository.count();

        HttpResult response =
                postAdvert(
                        token,
                        baseCreateBody(
                                uniqueTitle(),
                                location,
                                requireActiveAdvertTypeId(),
                                requireActiveCategoryId()));

        assertThat(response.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
        JsonNode root = JSON.readTree(response.body());
        assertThat(root.path("hasError").asBoolean()).isTrue();
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void managerJwt_onAdvertAdd_receivesForbidden() throws Exception {
        String token = registerManagerAndLogin();
        LocationIds location = requireSeededLocation();
        long countBefore = advertRepository.count();

        HttpResult response =
                postAdvert(
                        token,
                        baseCreateBody(
                                uniqueTitle(),
                                location,
                                requireActiveAdvertTypeId(),
                                requireActiveCategoryId()));

        assertThat(response.status()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void extraUserIdInBody_doesNotChangeOwner() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        long advertTypeId = requireActiveAdvertTypeId();
        long categoryId = requireActiveCategoryId();
        User customer = userRepository.findByEmail(lastRegisteredCustomerEmail()).orElseThrow();
        long foreignUserId = customer.getId() == 1L ? 999_999L : 1L;

        Map<String, Object> body = baseCreateBody(uniqueTitle(), location, advertTypeId, categoryId);
        body.put("userId", foreignUserId);
        body.put("slug", "client-forced-slug-value");
        body.put("advertStatus", "ACTIVATED");
        body.put("isActive", false);
        body.put("viewCount", 99);

        HttpResult response = postAdvert(token, body);
        assertThat(response.status()).isEqualTo(HttpStatus.CREATED.value());
        long advertId = JSON.readTree(response.body()).path("payload").path("id").asLong();
        createdAdvertIds.add(advertId);

        Advert stored = advertRepository.findById(advertId).orElseThrow();
        assertThat(stored.getUser().getId()).isEqualTo(customer.getId());
        assertThat(stored.getSlug()).isNotEqualTo("client-forced-slug-value");
        assertThat(stored.getViewCount()).isZero();
        assertThat(stored.isActive()).isTrue();
    }

    @Test
    void unknownCityId_doesNotCreateAdvert() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        long countBefore = advertRepository.count();

        Map<String, Object> body =
                baseCreateBody(
                        uniqueTitle(),
                        location,
                        requireActiveAdvertTypeId(),
                        requireActiveCategoryId());
        body.put("cityId", 9_999_999_999L);

        HttpResult response = postAdvert(token, body);

        assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void unauthenticated_onAdvertAdd_receivesUnauthorizedWithOriginalPath() throws Exception {
        LocationIds location = requireSeededLocation();
        Map<String, Object> body =
                baseCreateBody(
                        uniqueTitle(),
                        location,
                        requireActiveAdvertTypeId(),
                        requireActiveCategoryId());

        HttpResult response = postAdvertWithoutAuth(body);

        assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        JsonNode root = JSON.readTree(response.body());
        assertThat(root.path("hasError").asBoolean()).isTrue();
        assertThat(root.path("path").asText()).isEqualTo(ADVERT_ADD_PATH);
    }

    @Test
    void malformedJwt_onAdvertAdd_returnsUnauthorizedWithOriginalPath() throws Exception {
        LocationIds location = requireSeededLocation();
        Map<String, Object> body =
                baseCreateBody(
                        uniqueTitle(),
                        location,
                        requireActiveAdvertTypeId(),
                        requireActiveCategoryId());

        HttpResult response = postAdvert(MALFORMED_JWT, body);

        assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        JsonNode root = JSON.readTree(response.body());
        assertThat(root.path("path").asText()).isEqualTo(ADVERT_ADD_PATH);
        assertThat(response.body()).doesNotContain(resolvedJwtSecret());
    }

    @Test
    void inactiveCategory_doesNotCreateAdvert() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        long inactiveCategoryId = createCategory(false, false);
        long countBefore = advertRepository.count();

        HttpResult response =
                postAdvert(
                        token,
                        baseCreateBody(
                                uniqueTitle(),
                                location,
                                requireActiveAdvertTypeId(),
                                inactiveCategoryId));

        assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void deletedCategory_doesNotCreateAdvert() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        long deletedCategoryId = createCategory(true, true);
        long countBefore = advertRepository.count();

        HttpResult response =
                postAdvert(
                        token,
                        baseCreateBody(
                                uniqueTitle(),
                                location,
                                requireActiveAdvertTypeId(),
                                deletedCategoryId));

        assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    @Test
    void deletedAdvertType_doesNotCreateAdvert() throws Exception {
        String token = registerCustomerAndLogin();
        LocationIds location = requireSeededLocation();
        long deletedTypeId = createAdvertType(true);
        long countBefore = advertRepository.count();

        HttpResult response =
                postAdvert(
                        token,
                        baseCreateBody(
                                uniqueTitle(),
                                location,
                                deletedTypeId,
                                requireActiveCategoryId()));

        assertThat(response.status()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    private String lastRegisteredCustomerEmail;

    private String lastRegisteredCustomerEmail() {
        return lastRegisteredCustomerEmail;
    }

    private long createCategory(boolean active, boolean deleted) {
        Category category = new Category();
        category.setTitle("B21 Cat " + UUID.randomUUID().toString().substring(0, 8));
        category.setIcon("home");
        category.setBuiltIn(false);
        category.setSeq(99);
        category.setSlug("b21-cat-" + UUID.randomUUID().toString().substring(0, 8));
        category.setActive(active);
        category.setDeleted(deleted);
        Category saved = categoryRepository.saveAndFlush(category);
        createdCategoryIds.add(saved.getId());
        return saved.getId();
    }

    private long createAdvertType(boolean deleted) {
        AdvertType type = new AdvertType();
        type.setTitle("B21 Type " + UUID.randomUUID().toString().substring(0, 8));
        type.setDeleted(deleted);
        AdvertType saved = advertTypeRepository.saveAndFlush(type);
        createdAdvertTypeIds.add(saved.getId());
        return saved.getId();
    }

    private Map<String, Object> baseCreateBody(
            String title, LocationIds location, long advertTypeId, long categoryId) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("contentLanguage", "de");
        body.put("title", title);
        body.put("desc", "B2.1 integration advert");
        body.put("price", 850000);
        body.put("location", "Test location");
        body.put("advertTypeId", advertTypeId);
        body.put("countryId", location.countryId());
        body.put("cityId", location.cityId());
        body.put("districtId", location.districtId());
        body.put("categoryId", categoryId);
        return body;
    }

    private LocationIds requireSeededLocation() {
        List<LocationIds> rows =
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

    private long requireActiveAdvertTypeId() {
        return advertTypeRepository.findAll().stream()
                .filter(type -> !type.isDeleted())
                .findFirst()
                .orElseGet(
                        () -> {
                            AdvertType type = new AdvertType();
                            type.setTitle("B21 Sale");
                            AdvertType saved = advertTypeRepository.saveAndFlush(type);
                            createdAdvertTypeIds.add(saved.getId());
                            return saved;
                        })
                .getId();
    }

    private long requireActiveCategoryId() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.isActive() && !category.isDeleted())
                .findFirst()
                .orElseGet(
                        () -> {
                            Category category = new Category();
                            category.setTitle("B21 House");
                            category.setIcon("home");
                            category.setBuiltIn(false);
                            category.setSeq(1);
                            category.setSlug("b21-house-" + UUID.randomUUID().toString().substring(0, 8));
                            category.setActive(true);
                            Category saved = categoryRepository.saveAndFlush(category);
                            createdCategoryIds.add(saved.getId());
                            return saved;
                        })
                .getId();
    }

    private String uniqueTitle() {
        return "B21 Luxury Villa " + UUID.randomUUID().toString().substring(0, 8);
    }

    private String registerCustomerAndLogin() throws Exception {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        lastRegisteredCustomerEmail = "b21-customer-" + suffix + "@test.com";
        Map<String, Object> signUp =
                Map.of(
                        "firstName",
                        "B21",
                        "lastName",
                        "Customer",
                        "email",
                        lastRegisteredCustomerEmail,
                        "password",
                        CUSTOMER_PASSWORD,
                        "phone",
                        "9" + suffix + "0000001",
                        "gender",
                        "MALE",
                        "birthDate",
                        "01-01-1990");

        HttpRequest signUpRequest =
                HttpRequest.newBuilder(URI.create(absolute(SIGN_UP_PATH)))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(signUp)))
                        .build();
        HttpResponse<String> signUpResponse =
                HTTP.send(signUpRequest, HttpResponse.BodyHandlers.ofString());
        assertThat(signUpResponse.statusCode())
                .isIn(HttpStatus.OK.value(), HttpStatus.CREATED.value());
        JsonNode signUpRoot = JSON.readTree(signUpResponse.body());
        long userId = signUpRoot.path("payload").path("id").asLong();
        createdUserIds.add(userId);
        String signUpToken = signUpRoot.path("payload").path("token").asText();
        if (!signUpToken.isBlank()) {
            return signUpToken;
        }
        return login(lastRegisteredCustomerEmail, CUSTOMER_PASSWORD);
    }

    private String registerManagerAndLogin() throws Exception {
        registerCustomerAndLogin();
        long userId = userRepository.findByEmail(lastRegisteredCustomerEmail).orElseThrow().getId();
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id = ?", userId);
        jdbcTemplate.update(
                "INSERT INTO user_roles (user_id, role_id) SELECT ?, id FROM roles WHERE role_name = 'MANAGER'",
                userId);
        return login(lastRegisteredCustomerEmail, CUSTOMER_PASSWORD);
    }

    private String login(String email, String password) throws Exception {
        String json = JSON.writeValueAsString(Map.of("email", email, "password", password));
        HttpRequest request =
                HttpRequest.newBuilder(URI.create(absolute(LOGIN_PATH)))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        return JSON.readTree(response.body()).path("payload").path("token").asText();
    }

    private HttpResult postAdvert(String bearerOrToken, Map<String, Object> body) throws Exception {
        String authorization =
                bearerOrToken.startsWith("Bearer ") ? bearerOrToken : "Bearer " + bearerOrToken;
        HttpRequest request =
                HttpRequest.newBuilder(URI.create(absolute(ADVERT_ADD_PATH)))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .header("Authorization", authorization)
                        .POST(HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(body)))
                        .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        return new HttpResult(response.statusCode(), response.body());
    }

    private HttpResult postAdvertWithoutAuth(Map<String, Object> body) throws Exception {
        HttpRequest request =
                HttpRequest.newBuilder(URI.create(absolute(ADVERT_ADD_PATH)))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(JSON.writeValueAsString(body)))
                        .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        return new HttpResult(response.statusCode(), response.body());
    }

    private String absolute(String path) {
        return "http://localhost:" + port + path;
    }

    private record LocationIds(long countryId, long cityId, long districtId) {}

    private record HttpResult(int status, String body) {}
}