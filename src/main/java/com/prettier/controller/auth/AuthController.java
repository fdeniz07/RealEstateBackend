package com.prettier.controller.auth;

import com.prettier.payloads.request.concretes.LoginRequest;
import com.prettier.payloads.request.concretes.SignUpRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payloads.response.concretes.LoginResponse;
import com.prettier.payloads.response.concretes.SignUpResponse;
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
//@CrossOrigin //CrossOrigin tabanli istekleri destekledigini belirtiyoruz
public class AuthController {

    private final AuthService authService;
    private final  CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    //Not: signUp() ******************************************************************************************************
    @CrossOrigin //CrossOrigin tabanli istekleri destekledigini belirtiyoruz //Frontend tarafinda proxy kullanilarak cözülecek !!!
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/signUp")
    public InternalApiResponse<SignUpResponse> signUp(@PathVariable("language") Language language,
                                                      @Valid
                                                      @RequestBody SignUpRequest signUpRequest) {
        log.debug("[{}][signUp] -> request email: {}", this.getClass().getSimpleName(), signUpRequest.getEmail());

            SignUpResponse signUpResponse = authService.signUp(language, signUpRequest);
            log.debug(
                    "[{}][signUp] -> success userId: {} email: {}",
                    this.getClass().getSimpleName(),
                    signUpResponse != null ? signUpResponse.getId() : null,
                    signUpResponse != null ? signUpResponse.getEmail() : null);

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
        log.debug("[{}][login] -> request email: {}", this.getClass().getSimpleName(), loginRequest.getEmail());

        LoginResponse loginResponse = authService.login(language, loginRequest);

        log.debug("[{}][login] -> success email: {}", this.getClass().getSimpleName(), loginRequest.getEmail());

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