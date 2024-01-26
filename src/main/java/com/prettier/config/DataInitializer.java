package com.prettier.config;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.RoleType;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.RoleService;
import com.prettier.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner { // Uygulama ilk defa calistiginda builtin olarak gecilecek degerler

    private final RoleRepository roleRepository;
    private final RoleService roleService;

    //private final UserService userService;
    private final UserRepository userRepository;

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

        //!!! Admin olusturulacak built_in
        //User adminAccount = roleRepository.findByRoleName("ADMIN");
        //User adminAccount = userRepository.findByRole(roleRepository.findByRoleName("ADMIN"));
        if (userRepository.count() == 0) { //|| adminAccount == null

            User user = new User();
            user.setUserName("Admin");
            user.setFirstName("Super");
            user.setLastName("User");
            user.setEmail("admin@mail.com");
            user.setPhone("000000000000");
            user.setPasswordHash(new BCryptPasswordEncoder().encode("P4ssword")); //P4ssword
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
