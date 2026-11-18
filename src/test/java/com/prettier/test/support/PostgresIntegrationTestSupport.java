package com.prettier.test.support;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Shared PostgreSQL integration test configuration (DB-INIT-P1 Phase 2).
 *
 * <p>Activates Spring profile {@code test} and binds {@code real_estate_test_db} (or CI equivalent).
 * Never targets {@code real_estate_db}. See {@code docs/04-development/LOCAL_ENV.md}.
 */
@ActiveProfiles("test")
public abstract class PostgresIntegrationTestSupport {

    /** Local default when {@code SPRING_DATASOURCE_URL} is unset — not the development database. */
    public static final String DEFAULT_LOCAL_TEST_JDBC_URL =
            "jdbc:postgresql://127.0.0.1:5433/real_estate_test_db";

    private static final String ENV_URL = "SPRING_DATASOURCE_URL";
    private static final String ENV_USERNAME = "SPRING_DATASOURCE_USERNAME";
    private static final String ENV_PASSWORD = "SPRING_DATASOURCE_PASSWORD";
    private static final String ENV_JWT = "JWT_SECRET";

    private static final String ALLOWED_LOCAL_DB = "real_estate_test_db";
    private static final String ALLOWED_CI_DB = "real_estate_ci_test_db";
    private static final String FORBIDDEN_DEV_DB = "real_estate_db";

    @DynamicPropertySource
    static void registerPostgresIntegrationProperties(DynamicPropertyRegistry registry) {
        registerPostgresProperties(registry);
    }

    public static void registerPostgresProperties(DynamicPropertyRegistry registry) {
        String url = resolveJdbcUrl();
        String username = resolveUsername();
        String password = resolvePassword();
        String jwtSecret = resolveJwtSecret();

        assertIntegrationTestDatabase(url);

        registry.add("spring.datasource.url", () -> url);
        registry.add("spring.datasource.username", () -> username);
        registry.add("spring.datasource.password", () -> password);
        registry.add(ENV_JWT, () -> jwtSecret);
        registry.add("backendapi.app.jwtSecret", () -> jwtSecret);
        registry.add("backendapi.app.jwtExpressionMS", () -> 86_400_000L);
    }

  /** HTTP tests may call again to pin JWT properties; values match {@link #registerPostgresProperties}. */
    public static void registerHttpJwtProperties(DynamicPropertyRegistry registry) {
        String jwtSecret = resolveJwtSecret();
        registry.add("backendapi.app.jwtSecret", () -> jwtSecret);
        registry.add("backendapi.app.jwtExpressionMS", () -> 86_400_000L);
    }

    /** JWT secret from {@code JWT_SECRET} for integration tests (required; not production). */
    public static String resolvedJwtSecret() {
        return resolveJwtSecret();
    }

    public static void assertIntegrationTestDatabase(String jdbcUrl) {
        if (jdbcUrl == null || jdbcUrl.isBlank()) {
            throw illegalConfiguration("JDBC URL is blank.");
        }
        String databaseName = extractDatabaseName(jdbcUrl);
        if (FORBIDDEN_DEV_DB.equals(databaseName)) {
            throw illegalConfiguration(
                    "Integration tests must not use the development database '"
                            + FORBIDDEN_DEV_DB
                            + "'. Set "
                            + ENV_URL
                            + " to a test database (for example "
                            + DEFAULT_LOCAL_TEST_JDBC_URL
                            + "). See docs/04-development/LOCAL_ENV.md.");
        }
        if (!ALLOWED_LOCAL_DB.equals(databaseName) && !ALLOWED_CI_DB.equals(databaseName)) {
            throw illegalConfiguration(
                    "Unexpected database '"
                            + databaseName
                            + "'. Allowed: "
                            + ALLOWED_LOCAL_DB
                            + " or "
                            + ALLOWED_CI_DB
                            + ".");
        }
    }

    private static String resolveJdbcUrl() {
        String url = System.getenv(ENV_URL);
        if (url == null || url.isBlank()) {
            return DEFAULT_LOCAL_TEST_JDBC_URL;
        }
        return url.trim();
    }

    private static String resolveUsername() {
        String username = System.getenv(ENV_USERNAME);
        if (username == null || username.isBlank()) {
            return "db_user";
        }
        return username.trim();
    }

    private static String resolvePassword() {
        String password = System.getenv(ENV_PASSWORD);
        if (password == null || password.isBlank()) {
            throw illegalConfiguration(
                    ENV_PASSWORD
                            + " is not set. Export credentials before running integration tests (see docs/04-development/LOCAL_ENV.md).");
        }
        return password;
    }

    private static String resolveJwtSecret() {
        String jwt = System.getenv(ENV_JWT);
        if (jwt == null || jwt.isBlank()) {
            throw illegalConfiguration(
                    ENV_JWT
                            + " is not set. Export a dedicated integration-test JWT before running PostgreSQL"
                            + " tests (see docs/04-development/LOCAL_ENV.md). Do not use production JWT.");
        }
        return jwt.trim();
    }

    private static String extractDatabaseName(String jdbcUrl) {
        String path = jdbcUrl.substring(jdbcUrl.lastIndexOf('/') + 1);
        int query = path.indexOf('?');
        if (query >= 0) {
            path = path.substring(0, query);
        }
        return path;
    }

    private static IllegalStateException illegalConfiguration(String detail) {
        return new IllegalStateException(
                "PostgreSQL integration test configuration error: "
                        + detail
                        + " Profile 'test' and database '"
                        + ALLOWED_LOCAL_DB
                        + "' are required.");
    }
}