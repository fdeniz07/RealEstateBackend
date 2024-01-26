package com.prettier.controller.auth;

import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.payload.response.FriendlyMessage;
import com.prettier.payload.response.InternalApiResponse;
import com.prettier.payload.response.concretes.SignUpResponse;
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
@Tag(name = "Auth", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/auth")
@Slf4j //Log eklemek icin kullaniyoruz
public class AuthController {

    private final AuthService authService;

    //Not: signUp() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/signUp")
    public InternalApiResponse<SignUpResponse> signUp(@PathVariable("language") Language language,
                                                      @Valid
                                                      @RequestBody SignUpRequest signUpRequest)
    {
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

}
