package com.prettier.test.fixture;

import com.prettier.entity.concretes.City;
import com.prettier.entity.concretes.Country;
import com.prettier.entity.concretes.District;
import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.CitiesOfTurkey;
import com.prettier.entity.enums.Countries;
import com.prettier.entity.enums.DistrictsOfTurkey;
import com.prettier.entity.enums.RoleType;
import com.prettier.repository.CityRepository;
import com.prettier.repository.CountryRepository;
import com.prettier.repository.DistrictRepository;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.RoleService;
import com.prettier.test.support.PostgresIntegrationTestSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/**
 * Deterministic, idempotent reference data for profile {@code test} only (DB-INIT-P1 Phase 3).
 *
 * <p>Does not seed development/demo geography ({@code Countries} enum bulk load). Minimal TR location for advert tests.
 */
@Component
@Profile("test")
@RequiredArgsConstructor
public class TestReferenceDataFixture implements CommandLineRunner {

    /** Built-in admin identity — must match {@link com.prettier.config.DataInitializer} and auth integration tests. */
    public static final String BUILTIN_ADMIN_EMAIL = "admin@mail.com";

    /** Test-scoped password; never used in production configuration. */
    public static final String BUILTIN_ADMIN_PASSWORD = "P4ssword";

    static final String BUILTIN_ADMIN_ACTIVATION_TOKEN = "11111111-1111-1111-1111-111111111111";

    private static final String TURKEY_CODE = "TR";

    private final Environment environment;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    @Override
    public void run(String... args) {
        PostgresIntegrationTestSupport.assertIntegrationTestDatabase(
                Objects.requireNonNull(
                        environment.getProperty("spring.datasource.url"), "spring.datasource.url"));

        ensureRoles();
        ensureBuiltInAdmin();
        ensureMinimalTurkishGeography();
    }

    private void ensureRoles() {
        for (RoleType roleType : RoleType.values()) {
            if (roleRepository.existsByName(roleType.getRoleName())) {
                continue;
            }
            Role role = new Role();
            role.setName(roleType.getRoleName());
            role.setDescription(roleType.getDescription());
            roleRepository.save(role);
        }
    }

    private void ensureBuiltInAdmin() {
        if (userRepository.findByEmail(BUILTIN_ADMIN_EMAIL).isPresent()) {
            return;
        }
        Set<Role> adminRole = roleService.getByRoleName("ADMIN");
        if (adminRole == null || adminRole.isEmpty()) {
            throw new IllegalStateException(
                    "ADMIN role is missing; TestReferenceDataFixture cannot create built-in admin.");
        }

        User user = new User();
        user.setFirstName("Super");
        user.setLastName("User");
        user.setEmail(BUILTIN_ADMIN_EMAIL);
        user.setPhone("000000000000");
        user.setPasswordHash(new BCryptPasswordEncoder().encode(BUILTIN_ADMIN_PASSWORD));
        user.setActivationToken(BUILTIN_ADMIN_ACTIVATION_TOKEN);
        user.setBuiltIn(true);
        user.setActive(true);
        user.setRoles(adminRole);
        userRepository.save(user);
    }

    private void ensureMinimalTurkishGeography() {
        Country turkey =
                countryRepository.findAll().stream()
                        .filter(country -> TURKEY_CODE.equals(country.getCode()))
                        .findFirst()
                        .orElseGet(
                                () -> {
                                    Country country = new Country();
                                    country.setName(Countries.Turkey.getName());
                                    country.setCode(Countries.Turkey.getCode());
                                    return countryRepository.save(country);
                                });

        City testCity =
                cityRepository.findAll().stream()
                        .filter(
                                city ->
                                        city.getCountry() != null
                                                && TURKEY_CODE.equals(city.getCountry().getCode()))
                        .findFirst()
                        .orElseGet(
                                () -> {
                                    City city = new City();
                                    city.setName(CitiesOfTurkey.ADANA.getCityName());
                                    city.setPlateNumber(CitiesOfTurkey.ADANA.getPlateNumber());
                                    city.setCountry(turkey);
                                    return cityRepository.save(city);
                                });

        boolean districtExists =
                districtRepository.findAll().stream()
                        .anyMatch(
                                district ->
                                        district.getCity() != null
                                                && Objects.equals(
                                                        district.getCity().getId(), testCity.getId()));

        if (!districtExists) {
            District district = new District();
            district.setName(DistrictsOfTurkey.SEYHAN.getName());
            district.setCity(testCity);
            districtRepository.save(district);
        }
    }
}