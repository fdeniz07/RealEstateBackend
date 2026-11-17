package com.prettier.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * JWTUtils unit tests with a runtime-generated test-only HS256 key (Base64-encoded for {@link JWTUtils}
 * the same way production config supplies {@code backendapi.app.jwtSecret}).
 *
 * <p>This is not the production {@code backendapi.app.jwtSecret}.
 */
class JWTUtilsTest {

    private static final byte[] TEST_KEY_BYTES = randomSigningKeyBytes();

    private static final String TEST_JWT_SECRET_BASE64 =
            Base64.getEncoder().encodeToString(TEST_KEY_BYTES);

    private static final SecretKey TEST_SIGNING_KEY = Keys.hmacShaKeyFor(TEST_KEY_BYTES);

    private static final String TEST_EMAIL = "user@example.com";

    /**
     * HS256 token with fixed claims (legacy-style subject and long-lived expiry), minted at class load with the
     * current JJWT version. This does not preserve a byte-identical artifact from JJWT 0.11.5; it only verifies that
     * {@link JWTUtils} parses and validates a stable claim set signed with the test key.
     */
    private static final String TOKEN_WITH_FIXED_CLAIMS;

    static {
        TOKEN_WITH_FIXED_CLAIMS =
                Jwts.builder()
                        .subject("legacy-user@example.com")
                        .issuedAt(new Date(1_700_000_000_000L))
                        .expiration(new Date(4_102_444_800_000L))
                        .signWith(TEST_SIGNING_KEY, Jwts.SIG.HS256)
                        .compact();
    }

    private JWTUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JWTUtils();
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", TEST_JWT_SECRET_BASE64);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", 86_400_000L);
    }

    @Test
    void generateToken_exposesSubjectAsEmail() {
        UserDetails user = testUser(TEST_EMAIL);

        String token = jwtUtils.generateToken(user);

        assertThat(jwtUtils.getSubject(token)).isEqualTo(TEST_EMAIL);
    }

    @Test
    void generateToken_setsHs256InJwtHeader() {
        UserDetails user = testUser(TEST_EMAIL);
        String token = jwtUtils.generateToken(user);

        String algorithm =
                Jwts.parser()
                        .verifyWith(TEST_SIGNING_KEY)
                        .build()
                        .parseSignedClaims(token)
                        .getHeader()
                        .getAlgorithm();

        assertThat(algorithm).isEqualTo("HS256");
    }

    @Test
    void isTokenValid_acceptsFreshTokenForMatchingEmail() {
        UserDetails user = testUser(TEST_EMAIL);
        String token = jwtUtils.generateToken(user);

        assertThat(jwtUtils.isTokenValid(token, TEST_EMAIL)).isTrue();
    }

    @Test
    void isTokenValid_rejectsTamperedSignature() {
        UserDetails user = testUser(TEST_EMAIL);
        String token = jwtUtils.generateToken(user);

        String[] parts = token.split("\\.");
        String header = parts[0];
        String payload = parts[1];
        byte[] signatureBytes = Base64.getUrlDecoder().decode(parts[2]);
        signatureBytes[0] ^= (byte) 0x01;
        String tamperedSignature =
                Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
        String tampered = header + "." + payload + "." + tamperedSignature;

        assertThatThrownBy(() -> jwtUtils.isTokenValid(tampered, TEST_EMAIL))
                .isInstanceOf(JwtException.class);
    }

    @Test
    void getSubject_rejectsMalformedToken() {
        assertThatThrownBy(() -> jwtUtils.getSubject("not.a.valid.jwt"))
                .isInstanceOf(JwtException.class);
    }

    @Test
    void isTokenValid_rejectsExpiredToken() {
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", -60_000L);
        UserDetails user = testUser(TEST_EMAIL);
        String expiredToken = jwtUtils.generateToken(user);

        assertThatThrownBy(() -> jwtUtils.isTokenValid(expiredToken, TEST_EMAIL))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void parsesAndValidatesTokenWithFixedClaims() {
        assertThat(jwtUtils.getSubject(TOKEN_WITH_FIXED_CLAIMS))
                .isEqualTo("legacy-user@example.com");
        assertThat(jwtUtils.isTokenValid(TOKEN_WITH_FIXED_CLAIMS, "legacy-user@example.com"))
                .isTrue();
    }

    private static byte[] randomSigningKeyBytes() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private static UserDetails testUser(String email) {
        return User.withUsername(email).password("secret").roles("CUSTOMER").build();
    }
}
