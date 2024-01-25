package com.prettier.controller.auth;

import com.prettier.payload.mapper.UserMapper;
import com.prettier.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(value = "/{language}/register")
//    public InternalApiResponse<UserResponse> registerUser(@PathVariable("language") Language language,
//                                                          @RequestBody UserRequest userRequest) {
//        log.debug("[{}][registerUser] -> request: {}", this.getClass().getSimpleName(), userRequest);
//        UserResponse userResponse = userService.register(language, userRequest);
//
//        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
//
//        return InternalApiResponse.<UserResponse>builder()
//                .friendlyMessage(FriendlyMessage.builder()
//                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
//                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_SUCCESSFULLY_CREATED))
//                        .build())
//                .httpStatus(HttpStatus.CREATED)
//                .hasError(false)
//                .payload(userResponse)
//                .build();
//    }

    //Not: getAll() *********************************************************************************************************************************

    //  @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
//    @GetMapping(value = "/{language}/users") // http://localhost:8080/api/v1.0/users/EN/users
//    public InternalApiResponse<Page<UserResponse>> getAllWithPage(
//            @PathVariable("language") Language language,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "10") int size,
//            @RequestParam(value = "sort", defaultValue = "userName") String sort,
//            @RequestParam(value = "type", defaultValue = "desc") String type
//    ) {
//        log.debug("[{}][getUsers]", this.getClass().getSimpleName());
//        Page<UserResponse> userResponses = userService.getUsers(language, page, size, sort, type);
//
//        log.debug("[{}][getUsers] -> response: {}", this.getClass().getSimpleName(), userResponses);
//        return InternalApiResponse.<Page<UserResponse>>builder()
//                .httpStatus(HttpStatus.OK)
//                .hasError(false)
//                .payload(userResponses)
//                .build();
//    }



    //Not: login() ******************************************************************************************************
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping(value = "/{language}/register")
//    public InternalApiResponse<UserResponse> login(@PathVariable("language") Language language,
//                                                          @RequestBody UserRequest userRequest) {
//        log.debug("[{}][registerUser] -> request: {}", this.getClass().getSimpleName(), userRequest);
//        UserResponse userResponse = userService.login(language, userRequest);
//
//        log.debug("[{}][registerUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
//
//        return InternalApiResponse.<UserResponse>builder()
//                .friendlyMessage(FriendlyMessage.builder()
//                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
//                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_LOGIN_SUCCESSFULLY))
//                        .build())
//                .httpStatus(HttpStatus.CREATED)
//                .hasError(false)
//                .payload(userResponse)
//                .build();
//    }


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
