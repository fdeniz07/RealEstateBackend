package com.prettier.entity.concretes;

import com.prettier.entity.enums.TextDirection;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import com.prettier.test.support.PostgresIntegrationTestSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LanguageEntityMappingTest extends PostgresIntegrationTestSupport {

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