package com.prettier.controller.auth;

import com.prettier.payload.request.concretes.UserRequestForAdmin;
import com.prettier.payload.request.concretes.UserRoleChangeRequest;
import com.prettier.payload.response.concretes.UserResponseForAdmins;
import com.prettier.payload.response.concretes.UserRoleChangeResponse;
import com.prettier.service.abstracts.AdminService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/admins")
@Slf4j //Log eklemek icin kullaniyoruz
public class AdminController {

    private final AdminService adminService;
    private final UserService userService;

    //Not: getAllUsers() ******************************************************************************************************
    @GetMapping(value = "/{language}/getAllUsers") //http://localhost:8080/countries/getAll
    public InternalApiResponse<Page<UserResponseForAdmins>> getAllUsers(@PathVariable("language") Language language,
                                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                                        @RequestParam(value = "size", defaultValue = "41") int size,
                                                                        @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                        @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getAllUsers]", this.getClass().getSimpleName());
        Page<UserResponseForAdmins> userResponses = adminService.getAllUsers(language, page, size, sort, type);

        log.debug("[{}][getAllUsers] -> response: {}", this.getClass().getSimpleName(), userResponses);
        return InternalApiResponse.<Page<UserResponseForAdmins>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_LIST_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

    //Not: getAllUsersByActive() **********************************************************************************************
    @GetMapping(value = "/{language}/getAllUsersByActive") //http://localhost:8080/countries/getAll
    public InternalApiResponse<Page<UserResponseForAdmins>> getAllUsersByActive(@PathVariable("language") Language language,
                                                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(value = "size", defaultValue = "41") int size,
                                                                                @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                                @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getAllUsers]", this.getClass().getSimpleName());
        Page<UserResponseForAdmins> userResponses = adminService.getAllUsersByActive(language, page, size, sort, type);

        log.debug("[{}][getAllUsers] -> response: {}", this.getClass().getSimpleName(), userResponses);
        return InternalApiResponse.<Page<UserResponseForAdmins>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ACTIVE_USER_LIST_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

    //Not: getAllUsersByInactive() **********************************************************************************************
    @GetMapping(value = "/{language}/getAllUsersByInactive") //http://localhost:8080/countries/getAll
    public InternalApiResponse<Page<UserResponseForAdmins>> getAllUsersByInactive(@PathVariable("language") Language language,
                                                                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                  @RequestParam(value = "size", defaultValue = "41") int size,
                                                                                  @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                                  @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getAllUsers]", this.getClass().getSimpleName());
        Page<UserResponseForAdmins> userResponses = adminService.getAllUsersByInactive(language, page, size, sort, type);

        log.debug("[{}][getAllUsers] -> response: {}", this.getClass().getSimpleName(), userResponses);
        return InternalApiResponse.<Page<UserResponseForAdmins>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.INACTIVE_USER_LIST_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

    //Not: getAllManagers() ***************************************************************************************************
    @GetMapping(value = "/{language}/getAllManagers") //http://localhost:8080/api/v1.0/admins/EN/getAllManagers
    public InternalApiResponse<Page<UserResponseForAdmins>> getAllManagers(@PathVariable("language") Language language,
                                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                                           @RequestParam(value = "size", defaultValue = "41") int size,
                                                                           @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                           @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getAllManagers]", this.getClass().getSimpleName());
        Page<UserResponseForAdmins> userResponses = adminService.getAllManagers(language, page, size, sort, type);

        log.debug("[{}][getAllManagers] -> response: {}", this.getClass().getSimpleName(), userResponses);
        return InternalApiResponse.<Page<UserResponseForAdmins>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.MANAGER_LIST_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponses)
                .build();
    }


    //Not: getAllCustomers() **************************************************************************************************
    @GetMapping(value = "/{language}/getAllCustomers") //http://localhost:8080/countries/getAll
    public InternalApiResponse<Page<UserResponseForAdmins>> getAllCustomers(@PathVariable("language") Language language,
                                                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                                                            @RequestParam(value = "size", defaultValue = "41") int size,
                                                                            @RequestParam(value = "sort", defaultValue = "id") String sort,
                                                                            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getAllCustomers]", this.getClass().getSimpleName());
        Page<UserResponseForAdmins> userResponses = adminService.getAllCustomers(language, page, size, sort, type);

        log.debug("[{}][getAllCustomers] -> response: {}", this.getClass().getSimpleName(), userResponses);
        return InternalApiResponse.<Page<UserResponseForAdmins>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.CUSTOMERS_LIST_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

    //Not: addUser() **********************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/addUser")
    public InternalApiResponse<UserResponseForAdmins> addUser(@PathVariable("language") Language language,
                                                              @RequestBody @Valid UserRequestForAdmin userRequest
    ) {
        log.debug("[{}][addUser] -> request: {}", this.getClass().getSimpleName(), userRequest);
        UserResponseForAdmins userResponses = adminService.add(language, userRequest);

        log.debug("[{}][addUser] -> response: {}", this.getClass().getSimpleName(), userResponses);

        return InternalApiResponse.<UserResponseForAdmins>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

    //Not: changeUserRole() ***************************************************************************************************

    //!!! TODO : Bu kisim ilgili user login olduktan sonra bilgisi alinarak role degisimleri yapilacak !!!
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/changeUserRole/{userId}")
    public InternalApiResponse<UserRoleChangeResponse> changeUserRole(@PathVariable("language") Language language,
                                                                      @PathVariable @Valid UserRoleChangeRequest request,
                                                                      @PathVariable("userId") Long id
    ) {
        log.debug("[{}][changeUserRole] -> request: {}", this.getClass().getSimpleName(), id);
        UserRoleChangeResponse userResponses = adminService.changeUserRole(language, request,id );

        log.debug("[{}][changeUserRole] -> response: {}", this.getClass().getSimpleName(), userResponses);

        return InternalApiResponse.<UserRoleChangeResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_ROLE_CHANGED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

    //Not: changeUserStatus() *************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping(value = "/{language}/changeUserStatus/{userId}")
    public InternalApiResponse<UserResponseForAdmins> changeUserStatus(@PathVariable("language") Language language,
                                                                       @PathVariable("userId") Long id
    ) {
        log.debug("[{}][changeUserRole] -> request: {}", this.getClass().getSimpleName(), id);
        UserResponseForAdmins userResponses = adminService.changeUserStatus(language, id);

        log.debug("[{}][changeUserRole] -> response: {}", this.getClass().getSimpleName(), userResponses);

        return InternalApiResponse.<UserResponseForAdmins>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.USER_STATUS_CHANGED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(userResponses)
                .build();
    }

}
