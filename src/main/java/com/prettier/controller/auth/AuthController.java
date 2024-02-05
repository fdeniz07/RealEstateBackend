package com.prettier.controller.auth;

import com.prettier.payload.request.concretes.LoginRequest;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payload.response.concretes.LoginResponse;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.security.exception.CustomAuthenticationFailureHandler;
import com.prettier.service.abstracts.AuthService;
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
@Tag(name = "Auth", description = "Prettier Homes - Real Estate APIs")
@RequestMapping(value = "api/v1.0/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final  CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    //Not: signUp() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/signUp")
    public InternalApiResponse<SignUpResponse> signUp(@PathVariable("language") Language language,
                                                      @Valid
                                                      @RequestBody SignUpRequest signUpRequest) {
        log.debug("[{}][signUp] -> request: {}", this.getClass().getSimpleName(), signUpRequest);

            SignUpResponse signUpResponse = authService.signUp(language, signUpRequest);
            log.debug("[{}][signUp] -> response: {}", this.getClass().getSimpleName(), signUpResponse);

            return InternalApiResponse.<SignUpResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SIGN_UP_SUCCESSFULLY))
                            .build())
                    .httpStatus(HttpStatus.CREATED)
                    .hasError(false)
                    .payload(signUpResponse)
                    .build();
    }

    //Not: login() ******************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/{language}/login")
    public InternalApiResponse<LoginResponse> login(@PathVariable("language") Language language,
                                                    @Valid
                                                    @RequestBody LoginRequest loginRequest) {
        log.debug("[{}][login] -> request: {}", this.getClass().getSimpleName(), loginRequest);

        LoginResponse loginResponse = authService.login(language, loginRequest);

        log.debug("[{}][login] -> response: {}", this.getClass().getSimpleName(), loginResponse);

        return InternalApiResponse.<LoginResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.LOGIN_SUCCESSFULLY))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(loginResponse)
                .build();
    }
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