package com.prettier.entity.concretes;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Pure unit coverage for HIGH-1: bidirectional Advert↔User must not recurse in Lombok toString,
 * and User sensitive fields must stay out of toString output.
 */
class EntityToStringRecursionTest {

    private static final String PASSWORD_HASH = "hashed-password-value-for-toString-test";
    private static final String RESET_CODE = "reset-code-value-for-toString-test";
    private static final String ACTIVATION_TOKEN = "activation-token-value-for-toString-test";

    @Test
    void bidirectionalAdvertUserToStringDoesNotRecurseOrLeakSecrets() {
        User user = new User();
        user.setId(10L);
        user.setFirstName("Ada");
        user.setLastName("Lovelace");
        user.setEmail("ada@example.com");
        user.setPhone("000000000000");
        user.setPasswordHash(PASSWORD_HASH);
        user.setResetPasswordCode(RESET_CODE);
        user.setActivationToken(ACTIVATION_TOKEN);
        user.setAdvertSet(new HashSet<>());

        Advert advert = new Advert();
        advert.setId(20L);
        advert.setTitle("Sample Listing");
        advert.setSlug("sample-listing");
        advert.setPrice(100.0);
        advert.setUser(user);

        user.getAdvertSet().add(advert);

        assertThatCode(user::toString).doesNotThrowAnyException();
        assertThatCode(advert::toString).doesNotThrowAnyException();

        String userText = user.toString();
        String advertText = advert.toString();

        assertThat(userText).doesNotContain(PASSWORD_HASH);
        assertThat(userText).doesNotContain(RESET_CODE);
        assertThat(userText).doesNotContain(ACTIVATION_TOKEN);
        assertThat(userText).doesNotContain("passwordHash=");
        assertThat(userText).doesNotContain("resetPasswordCode=");
        assertThat(userText).doesNotContain("activationToken=");

        assertThat(advertText).contains("sample-listing");
        assertThat(advertText).doesNotContain("ada@example.com");
    }
}
