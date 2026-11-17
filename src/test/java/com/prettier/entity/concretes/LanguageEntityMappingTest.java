package com.prettier.entity.concretes;

import com.prettier.entity.enums.TextDirection;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LanguageEntityMappingTest {

    @DynamicPropertySource
    static void registerDataSource(DynamicPropertyRegistry registry) {
        String url = System.getenv().getOrDefault(
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

    @Autowired
    private EntityManager entityManager;

    @Test
    void loadsSeededLanguageFromV3Migration() {
        Language german = entityManager.find(Language.class, "de");

        assertNotNull(german);
        assertEquals("German", german.getName());
        assertEquals("Deutsch", german.getNativeName());
        assertTrue(german.isActive());
        assertTrue(german.isUiSupported());
        assertTrue(german.isContentSupported());
        assertEquals(TextDirection.LTR, german.getTextDirection());
        assertEquals((short) 10, german.getSortOrder());
        assertNull(german.getParent());
        assertNotNull(german.getCreatedAt());
        assertNotNull(german.getUpdatedAt());
    }

    @Test
    void persistsSelfReferencingParentRelationship() {
        Language parent = entityManager.find(Language.class, "en");
        assertNotNull(parent);

        Language child = Language.builder()
                .code("en-gb")
                .parent(parent)
                .name("English (United Kingdom)")
                .nativeName("English")
                .active(true)
                .uiSupported(true)
                .contentSupported(true)
                .textDirection(TextDirection.LTR)
                .sortOrder((short) 35)
                .build();

        entityManager.persist(child);
        entityManager.flush();
        entityManager.clear();

        Language loaded = entityManager.find(Language.class, "en-gb");
        assertNotNull(loaded);
        assertNotNull(loaded.getParent());
        assertEquals("en", loaded.getParent().getCode());

        entityManager.remove(loaded);
        entityManager.flush();
    }
}