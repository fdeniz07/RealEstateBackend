package com.prettier;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.enums.RoleType;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.service.abstracts.RoleService;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.utils.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
//@Configuration
//@Import({CreateObjectBean.class, OpenAPIConfig.class})

public class RealEstateApplication implements CommandLineRunner { //CommandLineRunner ile uygulama basladiginda calismasini istedigimiz kodlari calistir

    private final UserService userService;
    private final RoleService roleService;

    public RealEstateApplication(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    //Model Mapper - 1. Asama : Pom dosyasina ilgili dependency eklenir.
    //Model Mapper - 2. Asama : Main class'ina constructor Dependency Injection olarak gecilir.
//	@Bean
//	public ModelMapper modelMapper(){
//		return new ModelMapper();
//	}


    public static void main(String[] args) {
        SpringApplication.run(RealEstateApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {  //Uygulama baslatildiginda calismasini istedigimiz komut satirlari yazilir

        //!!! Role tablosunu doldur
        if (roleService.getAllUserRole().size() == 0) { //role tablosu bossa

            Language language = Language.EN;
            roleService.builtInSave(language,RoleType.ADMIN);
            roleService.builtInSave(language,RoleType.MANAGER);
            roleService.builtInSave(language,RoleType.CUSTOMER);
            roleService.builtInSave(language,RoleType.PERSONAL);

        }

        //!!! Admin olusturulacak built_in
        if (userService.countAllAdmin() == 0) {

            UserRequest admin = new UserRequest();
            admin.setUserName("Admin");
            admin.setFirstName("Super");
            admin.setLastName("User");
            admin.setEmail("admin@mail.com");
            admin.setPhone("000000000000");
            admin.setPasswordHash("123456");

            userService.register(Language.EN, admin);
        }
    }
}

