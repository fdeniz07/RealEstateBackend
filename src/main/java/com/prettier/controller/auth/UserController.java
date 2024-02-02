package com.prettier.controller.auth;

import com.prettier.payload.mapper.UserMapper;
import com.prettier.payload.request.concretes.CountryUpdateRequest;
import com.prettier.payload.request.concretes.UserUpdateRequest;
import com.prettier.payload.response.concretes.CountryResponse;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.service.abstracts.UserService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "User", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/users")
@Slf4j //Log eklemek icin kullaniyoruz
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    //Authorization Test
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/")
    public ResponseEntity<String> sayHello() {

        return ResponseEntity.ok("Hello from secured endpoint");
    }


    //Not: getUserById() ******************************************************************************************************
    @GetMapping(value = "/{language}/getUser") //http://localhost:8080/countries/getAll
    public InternalApiResponse<UserResponse> getUserById(@PathVariable("language") Language language,
                                                         @PathVariable("userId") Long id
    ) {
        log.debug("[{}][getUser]", this.getClass().getSimpleName());
        UserResponse userResponse = userService.getUserById(language, id);

        log.debug("[{}][getUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
        return InternalApiResponse.<UserResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();
    }


    //Not: updateUser() *******************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{userId}")
    public InternalApiResponse<UserResponse> update(@PathVariable("language") Language language,
                                                    @PathVariable("userId") Long id,
                                                    @RequestBody @Valid UserUpdateRequest userUpdateRequest
    ) {
        log.debug("[{}][updateUser] -> request: {} {}", this.getClass().getSimpleName(), id, userUpdateRequest);
        UserResponse userResponse = userService.update(language, userUpdateRequest, id);

        log.debug("[{}][updateUser] -> response: {}", this.getClass().getSimpleName(), userResponse);

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();
    }

    //Not: uploadProfileImage() ***********************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{language}/update/{userId}/profile-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public InternalApiResponse<UserResponse> uploadUserProfileImage(@PathVariable("language") Language language,
                                                                    @PathVariable("userId") Long id,
                                                                    @RequestParam("file") MultipartFile file
    ) {
        log.debug("[{}][uploadUserProfileImage] -> request: {}", this.getClass().getSimpleName(), id);
        UserResponse userResponse = userService.uploadUserProfileImage(language, id, file);

        log.debug("[{}][uploadUserProfileImage] -> response: {}", this.getClass().getSimpleName(), id);

        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_IMAGE_UPLOAD_SUCCESSFULLY))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();
    }

    //Not: getProfileImage() **************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{userId}/profile-image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public InternalApiResponse<byte[]> getCustomerProfileImage(@PathVariable("language") Language language,
                                                                    @PathVariable("userId") Long id
    ) {
        log.debug("[{}][uploadUserProfileImage] -> request: {}", this.getClass().getSimpleName(), id);
        UserResponse userResponse = userService.getUserProfileImage(language, id);

        log.debug("[{}][uploadUserProfileImage] -> response: {}", this.getClass().getSimpleName(), id);

        return InternalApiResponse.<byte[]>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
//                .payload(userResponse)
                .build();
    }


    //Not: deleteUser() *******************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{userId}")
    public InternalApiResponse<UserResponse> deleteCountry(@PathVariable("language") Language language,
                                                           @PathVariable("userId") Long id
    ) {
        log.debug("[{}][deleteUser] -> request userId: {}", this.getClass().getSimpleName(), id);
        UserResponse userResponse = userService.softDelete(language, id);

        log.debug("[{}][deleteUser] -> response: {}", this.getClass().getSimpleName(), userResponse);
        return InternalApiResponse.<UserResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponse)
                .build();
    }
}
