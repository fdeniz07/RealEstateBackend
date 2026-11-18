package com.prettier.service.concretes;

import com.prettier.RealEstateApplication;
import com.prettier.entity.concretes.AdvertType;
import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.entity.concretes.AdvertTranslation;
import com.prettier.payloads.request.concretes.AdvertCreateRequest;
import com.prettier.repository.AdvertRepository;
import com.prettier.repository.AdvertTranslationRepository;
import com.prettier.repository.AdvertTypeRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.shared.exception.exceptions.adverts.AdvertAlreadyExistsException;
import com.prettier.shared.utils.enums.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RealEstateApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdvertCreateRollbackIntegrationTest {

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

    @MockitoBean private AdvertTranslationRepository advertTranslationRepository;

    @Autowired private AdvertManager advertManager;
    @Autowired private AdvertRepository advertRepository;
    @Autowired private AdvertTypeRepository advertTypeRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void stubCustomerAndTranslationFailure() {
        User customer = ensureCustomer();
        UserDetails details =
                org.springframework.security.core.userdetails.User.withUsername(customer.getEmail())
                        .password("secret")
                        .authorities("CUSTOMER")
                        .build();
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities()));

        when(advertTranslationRepository.existsByLanguageCodeAndSlugIgnoreCase(anyString(), anyString()))
                .thenReturn(false);
        when(advertTranslationRepository.save(any(AdvertTranslation.class)))
                .thenThrow(new DataIntegrityViolationException("ux_advert_translations_lang_slug_lower"));
    }

    @Test
    void rollsBackAdvertWhenTranslationPersistenceFails() {
        long countBefore = advertRepository.count();
        AdvertCreateRequest request = buildCreateRequest("de", uniqueTitle());

        assertThatThrownBy(() -> advertManager.add(Language.EN, request))
                .isInstanceOf(AdvertAlreadyExistsException.class);
        assertThat(advertRepository.count()).isEqualTo(countBefore);
    }

    private User ensureCustomer() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream().anyMatch(role -> "CUSTOMER".equals(role.getName())))
                .findFirst()
                .orElseGet(
                        () -> {
                            Role customerRole =
                                    roleRepository.findAll().stream()
                                            .filter(role -> "CUSTOMER".equals(role.getName()))
                                            .findFirst()
                                            .orElseThrow();
                            User user = new User();
                            user.setFirstName("Rollback");
                            user.setLastName("Customer");
                            user.setEmail("rollback-customer-" + UUID.randomUUID() + "@test.com");
                            user.setPhone("000000000002");
                            user.setPasswordHash("hash");
                            user.setActive(true);
                            user.setBuiltIn(false);
                            user.setRoles(Set.of(customerRole));
                            return userRepository.saveAndFlush(user);
                        });
    }

    private AdvertCreateRequest buildCreateRequest(String contentLanguage, String title) {
        LocationIds location = requireSeededLocation();
        AdvertType advertType = new AdvertType();
        advertType.setTitle("Type " + UUID.randomUUID().toString().substring(0, 6));
        advertType = advertTypeRepository.saveAndFlush(advertType);
        Category category =
                categoryRepository.saveAndFlush(
                        Category.builder()
                                .title("Cat " + UUID.randomUUID().toString().substring(0, 6))
                                .icon("home")
                                .builtIn(false)
                                .seq(1)
                                .slug("cat-" + UUID.randomUUID().toString().substring(0, 8))
                                .active(true)
                                .build());

        return AdvertCreateRequest.builder()
                .contentLanguage(contentLanguage)
                .title(title)
                .desc("Rollback test")
                .price(250_000D)
                .location("Test")
                .advertTypeId(advertType.getId())
                .countryId(location.countryId())
                .cityId(location.cityId())
                .districtId(location.districtId())
                .categoryId(category.getId())
                .build();
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
        return rows.get(0);
    }

    private static String uniqueTitle() {
        return "Rollback Villa " + UUID.randomUUID().toString().substring(0, 8);
    }

    private record LocationIds(long countryId, long cityId, long districtId) {}
}