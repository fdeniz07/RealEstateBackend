package com.prettier.test.fixture;

import com.prettier.RealEstateApplication;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.test.support.PostgresIntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = RealEstateApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TestReferenceDataFixtureSmokeTest extends PostgresIntegrationTestSupport {

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private JdbcTemplate jdbcTemplate;

    @Test
    void testProfileLoadsRolesAdminAndTrGeography() {
        assertThat(roleRepository.count()).isGreaterThanOrEqualTo(1);
        assertThat(roleRepository.existsByName("ADMIN")).isTrue();
        assertThat(roleRepository.existsByName("CUSTOMER")).isTrue();
        assertThat(userRepository.findByEmail(TestReferenceDataFixture.BUILTIN_ADMIN_EMAIL))
                .isPresent();

        Integer trLocations =
                jdbcTemplate.queryForObject(
                        """
                        SELECT COUNT(*)
                        FROM countries c
                        JOIN cities ci ON ci.country_id = c.id
                        JOIN districts d ON d.city_id = ci.id
                        WHERE c.code = 'TR'
                        """,
                        Integer.class);
        assertThat(trLocations).isGreaterThanOrEqualTo(1);
    }
}