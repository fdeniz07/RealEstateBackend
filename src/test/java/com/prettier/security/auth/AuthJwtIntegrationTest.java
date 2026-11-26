package com.prettier.security.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prettier.RealEstateApplication;
import com.prettier.test.fixture.TestReferenceDataFixture;
import com.prettier.test.support.PostgresIntegrationTestSupport;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * HTTP-level JWT authentication integration tests (B1.3b-2).
 *
 * <p>Uses the built-in admin seeded by {@link TestReferenceDataFixture} under profile {@code test}; credentials match
 * that fixture only (not printed in logs). JWT signing uses the shared test secret from
 * {@link PostgresIntegrationTestSupport}, not production {@code application.yaml} values.
 *
 * <p>B1.4a JWT filter catches parse/user-load failures and continues the chain; protected-endpoint 401
 * responses must report the original URI in {@code path}, not {@code /error}.
 *
 * <p>Uses {@link HttpClient} with {@link LocalServerPort} because Spring Boot 4.1.2 exposes {@code TestRestTemplate}
 * via {@code spring-boot-starter-webmvc-test}, which is not on the classpath yet.
 */
@SpringBootTest(
        classes = RealEstateApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthJwtIntegrationTest extends PostgresIntegrationTestSupport {

    private static SecretKey applicationSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(resolvedJwtSecret()));
    }

    private static SecretKey mismatchSigningKey() {
        return distinctHs256Key(applicationSigningKey());
    }

    private static String mismatchKeyBase64ForLeakAssert() {
        return Base64.getEncoder().encodeToString(mismatchSigningKey().getEncoded());
    }

    private static final String PROTECTED_ROLES_PATH = "/api/v1.0/roles/EN/roles";
    private static final String LOGIN_PATH = "/api/v1.0/auth/EN/login";

    private static final String BUILTIN_ADMIN_EMAIL = TestReferenceDataFixture.BUILTIN_ADMIN_EMAIL;
    private static final String BUILTIN_ADMIN_PASSWORD = TestReferenceDataFixture.BUILTIN_ADMIN_PASSWORD;

    private static final String MALFORMED_JWT = "not.a.valid.jwt";

    @LocalServerPort private int port;

    private static final ObjectMapper JSON = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    private String absolute(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void login_success_returnsHttp200AndNonBlankToken() throws Exception {
        HttpResult response = postLogin(BUILTIN_ADMIN_EMAIL, BUILTIN_ADMIN_PASSWORD);

        assertThat(response.status()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.contentType()).contains("application/json");

        JsonNode root = JSON.readTree(response.body());
        assertThat(root.path("hasError").asBoolean()).isFalse();
        assertThat(root.path("payload").path("token").asText()).isNotBlank();
        assertThat(root.path("payload").has("password")).isFalse();
        assertThat(response.body()).doesNotContain("passwordHash");
    }

    @Test
    void signUp_success_responseOmitsPasswordAndPasswordHash() throws Exception {
        String suffix = String.valueOf(System.nanoTime()).substring(0, 8);
        String email = "high2-signup-" + suffix + "@test.com";
        String password = "SecretPass1";
        String json =
                JSON.writeValueAsString(
                        Map.of(
                                "firstName", "High",
                                "lastName", "Two",
                                "email", email,
                                "password", password,
                                "phone", "9" + suffix + "0000001",
                                "gender", "UNKNOWN",
                                "birthDate", "01-01-1995"));

        HttpRequest request =
                HttpRequest.newBuilder(URI.create(absolute("/api/v1.0/auth/EN/signUp")))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();
        HttpResponse<String> httpResponse = HTTP.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(httpResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        JsonNode root = JSON.readTree(httpResponse.body());
        assertThat(root.path("hasError").asBoolean()).isFalse();
        assertThat(root.path("payload").path("token").asText()).isNotBlank();
        assertThat(root.path("payload").path("email").asText()).isEqualTo(email);
        assertThat(root.path("payload").has("password")).isFalse();
        assertThat(httpResponse.body()).doesNotContain("passwordHash");
        assertThat(httpResponse.body()).doesNotContain(password);
    }

    @Test
    void login_tokenSubjectEqualsAuthenticatedUserEmail() throws Exception {
        String token = loginAndExtractToken(BUILTIN_ADMIN_EMAIL, BUILTIN_ADMIN_PASSWORD);
        String subject = parseSubject(token);
        assertThat(subject).isEqualTo(BUILTIN_ADMIN_EMAIL);
    }

    @Test
    void validJwt_grantsAccessToProtectedRolesEndpoint() throws Exception {
        String token = loginAndExtractToken(BUILTIN_ADMIN_EMAIL, BUILTIN_ADMIN_PASSWORD);

        HttpResult response = getRoles("Bearer " + token);

        assertThat(response.status()).isEqualTo(HttpStatus.OK.value());
        JsonNode root = JSON.readTree(response.body());
        assertThat(root.path("hasError").asBoolean()).isFalse();
        assertThat(root.path("payload").isMissingNode()).isFalse();
    }

    @Test
    void missingJwt_returnsUnauthorizedWithErrorEnvelope() throws Exception {
        HttpResult response = getRoles(null);

        assertJwtUnauthorizedOnProtectedPath(response);
    }

    @Test
    void malformedJwt_returnsUnauthorizedWithErrorEnvelope() throws Exception {
        HttpResult response = getRoles("Bearer " + MALFORMED_JWT);

        assertJwtUnauthorizedOnProtectedPath(response);
    }

    @Test
    void expiredJwt_signedWithTestKey_returnsUnauthorizedWithErrorEnvelope() throws Exception {
        String expiredToken =
                buildHs256Token(BUILTIN_ADMIN_EMAIL, applicationSigningKey(), -60_000L);

        HttpResult response = getRoles("Bearer " + expiredToken);

        assertJwtUnauthorizedOnProtectedPath(response);
    }

    @Test
    void invalidSignatureJwt_returnsUnauthorizedWithOriginalPath() throws Exception {
        String mismatchedToken =
                buildHs256Token(BUILTIN_ADMIN_EMAIL, mismatchSigningKey(), 86_400_000L);

        HttpResult response = getRoles("Bearer " + mismatchedToken);

        assertJwtUnauthorizedOnProtectedPath(response);
    }

    @Test
    void invalidPassword_returnsUnauthorizedWithErrorEnvelope() throws Exception {
        HttpResult response = postLogin(BUILTIN_ADMIN_EMAIL, "WrongPass1");

        assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        JsonNode root = JSON.readTree(response.body());
        assertUnauthorizedInternalApiResponse(root);
        assertThat(root.path("path").asText()).isEqualTo(LOGIN_PATH);
    }

    private HttpResult postLogin(String email, String password) throws Exception {
        String json = JSON.writeValueAsString(Map.of("email", email, "password", password));
        HttpRequest request =
                HttpRequest.newBuilder(URI.create(absolute(LOGIN_PATH)))
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();
        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        return new HttpResult(
                response.statusCode(),
                response.body(),
                response.headers().firstValue("Content-Type").orElse(""));
    }

    private String loginAndExtractToken(String email, String password) throws Exception {
        HttpResult response = postLogin(email, password);
        assertThat(response.status()).isEqualTo(HttpStatus.OK.value());
        JsonNode root = JSON.readTree(response.body());
        return root.path("payload").path("token").asText();
    }

    private HttpResult getRoles(String authorizationHeader) throws Exception {
        HttpRequest.Builder builder =
                HttpRequest.newBuilder(URI.create(absolute(PROTECTED_ROLES_PATH + "?page=0&size=1")))
                        .GET()
                        .header("Accept", "application/json");
        if (authorizationHeader != null) {
            builder.header("Authorization", authorizationHeader);
        }
        HttpResponse<String> response = HTTP.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        return new HttpResult(
                response.statusCode(),
                response.body(),
                response.headers().firstValue("Content-Type").orElse(""));
    }

    private static void assertJwtUnauthorizedOnProtectedPath(HttpResult response) throws Exception {
        assertThat(response.status()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        JsonNode root = JSON.readTree(response.body());
        assertUnauthorizedInternalApiResponse(root);
        assertThat(root.path("path").asText()).isEqualTo(PROTECTED_ROLES_PATH);
        assertThat(root.path("path").asText()).isNotEqualTo("/error");
        assertThat(response.body()).doesNotContain(resolvedJwtSecret());
        assertThat(response.body()).doesNotContain(mismatchKeyBase64ForLeakAssert());
    }

    private static void assertUnauthorizedInternalApiResponse(JsonNode root) {
        assertThat(root.path("hasError").asBoolean()).isTrue();
        assertThat(root.path("errorMessages").isArray()).isTrue();
        assertThat(root.path("errorMessages").size()).isGreaterThan(0);
        assertThat(root.path("friendlyMessage").path("title").asText()).isNotBlank();
        String httpStatus = root.path("httpStatus").asText();
        assertThat(httpStatus).containsIgnoringCase("401");
    }

    private static String parseSubject(String token) {
        return Jwts.parser()
                .verifyWith(applicationSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private static String buildHs256Token(
            String subjectEmail, SecretKey signingKey, long expirationOffsetMs) {
        long now = System.currentTimeMillis();
        long issuedAtOffset = expirationOffsetMs < 0 ? expirationOffsetMs - 60_000L : 0L;
        return Jwts.builder()
                .subject(subjectEmail)
                .issuedAt(new Date(now + issuedAtOffset))
                .expiration(new Date(now + expirationOffsetMs))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    private static SecretKey distinctHs256Key(SecretKey applicationKey) {
        byte[] applicationBytes = applicationKey.getEncoded();
        byte[] randomBytes = new byte[32];
        SecureRandom random = new SecureRandom();
        do {
            random.nextBytes(randomBytes);
        } while (Arrays.equals(randomBytes, applicationBytes));
        return Keys.hmacShaKeyFor(randomBytes);
    }

    private record HttpResult(int status, String body, String contentType) {}
}
