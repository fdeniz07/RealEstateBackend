package com.prettier.controller.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/admins")
@Slf4j //Log eklemek icin kullaniyoruz
public class AdminController {

    //Not: getAllUsers() ******************************************************************************************************

    //Not: getAllUsersByActive() **********************************************************************************************

    //Not: getAllManagers() ***************************************************************************************************

    //Not: getAllCustomers() **************************************************************************************************

    //Not: getUserById() ********************/*********************************************************************************

    //Not: deleteUser() *******************************************************************************************************

    //Not: changeUserRole() ***************************************************************************************************

    //Not: changeUserStatus() *************************************************************************************************

}
