package com.prettier.controller;

import com.prettier.payload.mapper.UserMapper;
import com.prettier.payload.request.concretes.AdvertRequest;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.FriendlyMessage;
import com.prettier.payload.response.InternalApiResponse;
import com.prettier.payload.response.concretes.AdvertResponse;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/users")
@Slf4j //Log eklemek icin kullaniyoruz
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    //Not: registerUser() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/register")
    public InternalApiResponse<UserResponse> registerUser(@PathVariable("language") Language language,
                                                          @RequestBody UserRequest userRequest) {
        log.debug("[{}][registerUser] -> request: {}", this.getClass().getSimpleName(), userRequest);
        UserResponse userResponse = userService.register(language, userRequest);

        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(userResponse)
                .build();
    }


    //Not: login() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/register")
    public InternalApiResponse<UserResponse> login(@PathVariable("language") Language language,
                                                          @RequestBody UserRequest userRequest) {
        log.debug("[{}][registerUser] -> request: {}", this.getClass().getSimpleName(), userRequest);
        UserResponse userResponse = userService.login(language, userRequest);

        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_LOGIN_SUCCESSFULLY))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(userResponse)
                .build();
    }


    /*
        {
          "firstName": "Elia",
          "lastName": "Doe",
          "email": "elia.doe@mail.com",
          "userName": "EliaDoe",
          "phone": "222-2223-4445",
          "passwordHash": "P4ssw0rd",
          "roleIds": [
            1
          ],
          "builtIn": false,
          "active": true
        }

       {
          "firstName": "John",
          "lastName": "Doe",
          "email": "john.doe@mail.com",
          "userName": "JohnDoe",
          "phone": "111-2223-4445",
          "passwordHash": "P4ssw0rd",
          "roleIds": [
            2
          ],
          "builtIn": false,
          "active": true
       }

     {
          "firstName": "Martin",
          "lastName": "Müller",
          "email": "martin@muller.com",
          "userName": "MMuller",
          "phone": "333-2223-4445",
          "passwordHash": "P4ssw0rd",
          "roleIds": [
            3
          ],
          "builtIn": false,
          "active": true
     }

             {
          "firstName": "Sam",
          "lastName": "Koch",
          "email": "sam-koch@mail.com",
          "userName": "SamKoch",
          "phone": "3333-2223-4445",
          "passwordHash": "P4ssw0rd",
          "roleIds": [
            2
          ],
          "builtIn": false,
          "active": true
        }

    */
}
