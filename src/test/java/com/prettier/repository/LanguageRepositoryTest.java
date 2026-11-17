package com.prettier.repository;

import com.prettier.entity.concretes.Language;
import com.prettier.entity.enums.TextDirection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LanguageRepositoryTest {

  private static final String TEMP_CHILD = "en-gb";
  private static final String TEMP_INACTIVE = "xx-inactive";
  private static final String TEMP_NO_UI = "xx-no-ui";
  private static final String TEMP_NO_CONTENT = "xx-no-content";

  @DynamicPropertySource
  static void registerDataSource(DynamicPropertyRegistry registry) {
    String url =
        System.getenv()
            .getOrDefault("SPRING_DATASOURCE_URL", "jdbc:postgresql://localhost:5433/real_estate_db");
    String username = System.getenv().getOrDefault("SPRING_DATASOURCE_USERNAME", "db_user");
    String password = System.getenv().getOrDefault("SPRING_DATASOURCE_PASSWORD", "db_password");
    registry.add("spring.datasource.url", () -> url);
    registry.add("spring.datasource.username", () -> username);
    registry.add("spring.datasource.password", () -> password);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    registry.add("spring.flyway.enabled", () -> "true");
  }

  @Autowired private LanguageRepository languageRepository;

  @AfterEach
  void removeTemporaryRows() {
    for (String code : List.of(TEMP_CHILD, TEMP_INACTIVE, TEMP_NO_UI, TEMP_NO_CONTENT)) {
      if (languageRepository.existsById(code)) {
        languageRepository.deleteById(code);
        languageRepository.flush();
      }
    }
  }

  @Test
  void findsSeededLanguagesByPrimaryKey() {
    Optional<Language> german = languageRepository.findById("de");
    Optional<Language> turkish = languageRepository.findById("tr");
    Optional<Language> english = languageRepository.findById("en");

    assertTrue(german.isPresent());
    assertTrue(turkish.isPresent());
    assertTrue(english.isPresent());
    assertEquals("German", german.get().getName());
    assertEquals("Turkish", turkish.get().getName());
    assertEquals("English", english.get().getName());
  }

  @Test
  void findActiveLanguagesOrderedBySortOrderThenCode() {
    persistTemporaryFixtures();

    List<Language> active = languageRepository.findByActiveTrueOrderBySortOrderAscCodeAsc();

    assertTrue(active.size() >= 3);
    assertCodesInOrder(active, "de", "tr", "en");
    assertOrderedBySortOrderThenCode(active);
    assertFalse(active.stream().anyMatch(l -> TEMP_INACTIVE.equals(l.getCode())));
  }

  @Test
  void findUiSupportedActiveLanguagesExcludesUnsupported() {
    persistTemporaryFixtures();

    List<Language> ui =
        languageRepository.findByActiveTrueAndUiSupportedTrueOrderBySortOrderAscCodeAsc();

    assertTrue(ui.stream().noneMatch(l -> TEMP_NO_UI.equals(l.getCode())));
    assertTrue(ui.stream().anyMatch(l -> "de".equals(l.getCode())));
    assertOrderedBySortOrderThenCode(ui);
  }

  @Test
  void findContentSupportedActiveLanguagesExcludesUnsupported() {
    persistTemporaryFixtures();

    List<Language> content =
        languageRepository.findByActiveTrueAndContentSupportedTrueOrderBySortOrderAscCodeAsc();

    assertTrue(content.stream().noneMatch(l -> TEMP_NO_CONTENT.equals(l.getCode())));
    assertTrue(content.stream().anyMatch(l -> "tr".equals(l.getCode())));
    assertOrderedBySortOrderThenCode(content);
  }

  @Test
  void findsChildrenByParentCodeAndResolvesParentOnChild() {
    Language parent = languageRepository.findById("en").orElseThrow();
    Language child =
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
            .build();
    languageRepository.saveAndFlush(child);

    List<Language> children =
        languageRepository.findByParent_CodeOrderBySortOrderAscCodeAsc("en");

    assertTrue(children.stream().anyMatch(l -> TEMP_CHILD.equals(l.getCode())));

    Language loaded = languageRepository.findById(TEMP_CHILD).orElseThrow();
    assertNotNull(loaded.getParent());
    assertEquals("en", loaded.getParent().getCode());
  }

  @Test
  void seededLanguagesHaveNoParent() {
    assertNull(languageRepository.findById("de").orElseThrow().getParent());
  }

  private void persistTemporaryFixtures() {
    languageRepository.save(
        Language.builder()
            .code(TEMP_INACTIVE)
            .name("Inactive Test")
            .nativeName("Inactive")
            .active(false)
            .uiSupported(true)
            .contentSupported(true)
            .textDirection(TextDirection.LTR)
            .sortOrder((short) 5)
            .build());
    languageRepository.save(
        Language.builder()
            .code(TEMP_NO_UI)
            .name("No UI Test")
            .nativeName("No UI")
            .active(true)
            .uiSupported(false)
            .contentSupported(true)
            .textDirection(TextDirection.LTR)
            .sortOrder((short) 906)
            .build());
    languageRepository.save(
        Language.builder()
            .code(TEMP_NO_CONTENT)
            .name("No Content Test")
            .nativeName("No Content")
            .active(true)
            .uiSupported(true)
            .contentSupported(false)
            .textDirection(TextDirection.LTR)
            .sortOrder((short) 907)
            .build());
    languageRepository.flush();
  }

  private static void assertCodesInOrder(List<Language> languages, String... expectedPrefix) {
    for (int i = 0; i < expectedPrefix.length; i++) {
      assertEquals(expectedPrefix[i], languages.get(i).getCode());
    }
  }

  private static void assertOrderedBySortOrderThenCode(List<Language> languages) {
    for (int i = 1; i < languages.size(); i++) {
      Language prev = languages.get(i - 1);
      Language curr = languages.get(i);
      int sortCompare = Short.compare(prev.getSortOrder(), curr.getSortOrder());
      final int index = i;
      assertTrue(
          sortCompare < 0
              || (sortCompare == 0 && prev.getCode().compareTo(curr.getCode()) <= 0),
          () ->
              "expected sort_order then code order at index "
                  + index
                  + " ("
                  + prev.getCode()
                  + " vs "
                  + curr.getCode()
                  + ")");
    }
  }
}