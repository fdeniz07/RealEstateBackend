package com.prettier.config;

import com.prettier.entity.concretes.*;
import com.prettier.entity.enums.CitiesOfTurkey;
import com.prettier.entity.enums.Countries;
import com.prettier.entity.enums.DistrictsOfTurkey;
import com.prettier.entity.enums.RoleType;
import com.prettier.repository.*;
import com.prettier.service.abstracts.CityService;
import com.prettier.service.abstracts.CountryService;
import com.prettier.service.abstracts.DistrictService;
import com.prettier.service.abstracts.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner { // Uygulama ilk defa calistiginda builtin olarak gecilecek degerler

    private final RoleService roleService;
    private final CityService cityService;
    private final CountryService countryService;
    private final DistrictService districtService;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final DistrictRepository districtRepository;


    @Override
    public void run(String... args) throws Exception {

        // RoleType Enum'daki değerleri Role tablosuna ekliyoruz
        if (roleService.getAllUserRole().isEmpty()) { //role tablosu bossa
            for (RoleType roleType : RoleType.values()) {
                Role role = new Role();
                role.setName(roleType.getRoleName());
                role.setDescription(roleType.getDescription());
                roleRepository.save(role);
            }
        }

        // Countries Enum'daki değerleri Countries tablosuna ekliyoruz
        if (countryService.getAllCountries().isEmpty()) {
            for (Countries countries : Countries.values()) {
                Country country = new Country();
                country.setName(countries.getName());
                country.setCode(countries.getCode());
                countryRepository.save(country);
            }
        }

        // CitiesOfTurkey Enum'daki değerleri Cities tablosuna ekliyoruz
        if (cityService.getAllCities().isEmpty()) {
            for (CitiesOfTurkey cities : CitiesOfTurkey.values()) {
                City city = new City();
                city.setName(cities.getCityName());
                city.setPlateNumber(cities.getPlateNumber());

                Country turkeyCode = countryService.getById(222);
                city.setCountry(turkeyCode);
                cityRepository.save(city);

            }
        }

        // DistrictsOfTurkey Enum'daki değerleri Districts tablosuna ekliyoruz
        if (districtService.getAllDistricts().isEmpty()) {
            for (DistrictsOfTurkey districts : DistrictsOfTurkey.values()) {
                District district = new District();
                district.setName(districts.getName());

                City city = cityService.getById(districts.getCity_id());
                district.setCity(city);
                districtRepository.save(district);
            }
        }

        //!!! Admin olusturulacak built_in
        //User adminAccount = roleRepository.findByRoleName("ADMIN");
        //User adminAccount = userRepository.findByRole(roleRepository.findByRoleName("ADMIN"));
        if (userRepository.count() == 0) { //|| adminAccount == null

            User user = new User();
            user.setUsername("Admin");
            user.setFirstName("Super");
            user.setLastName("User");
            user.setEmail("admin@mail.com");
            user.setPhone("000000000000");
            user.setPasswordHash(new BCryptPasswordEncoder().encode("P4ssword")); //P4ssword
            user.setActivationToken(UUID.randomUUID().toString());
            user.setBuiltIn(true);
            user.setActive(true);

            //DB deki role tablosunda ADMIN rolünü getir
            Set<Role> adminRole = roleService.getByRoleName("ADMIN");
            user.setRoles(adminRole);
            userRepository.save(user);

//            Role adminRole = roleService.getRoles()
//                    .stream()
//                    .filter(roleName -> roleName.getName().equals("ADMIN"))
//                    .findFirst()
//                    .orElse(null);

            //Eger role null degilse bu kullaniciyi built'in olarak Admin ata.
//            if (adminRole != null) {
//                user.setRoles();
//            }
        }
    }
}
