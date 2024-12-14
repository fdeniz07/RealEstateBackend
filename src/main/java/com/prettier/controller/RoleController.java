package com.prettier.controller;

import com.prettier.payloads.mapper.RoleMapper;
import com.prettier.payloads.request.concretes.RoleRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payloads.response.concretes.RoleResponse;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
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
@Tag(name = "Role", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/roles")
@Slf4j //Log eklemek icin kullaniyoruz
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    //Not: getAll() *********************************************************************************************************************************

    @GetMapping(value = "/{language}/roles") // http://localhost:8080/cities/EN/cities
    public InternalApiResponse<Page<RoleResponse>> getRoles(@PathVariable("language") Language language,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "41") int size,
                                                             @RequestParam(value = "sort", defaultValue = "name") String sort,
                                                             @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        log.debug("[{}][getRoles]", this.getClass().getSimpleName());
        Page<RoleResponse> roleResponses = roleService.getRoles(language, page, size, sort, type);

        log.debug("[{}][getRoles] -> response: {}", this.getClass().getSimpleName(), roleResponses);
        return InternalApiResponse.<Page<RoleResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(roleResponses)
                .build();
    }


    //Not: getById() ****************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/getById/{roleId}")
    public InternalApiResponse<RoleResponse> getRoleById(@PathVariable("language") Language language,
                                                         @PathVariable("roleId") Long id) {
        log.debug("[{}][getRoleById] -> request: {}", this.getClass().getSimpleName(), id);
        RoleResponse roleResponse = roleService.getById(language, id);

        log.debug("[{}][getRoleById] -> response: {}", this.getClass().getSimpleName(), roleResponse);

        return InternalApiResponse.<RoleResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(roleResponse)
                .build();
    }



    //Not: addRole() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/addRole")
    public InternalApiResponse<RoleResponse> add(@PathVariable("language") Language language,
                                                 @Valid @RequestBody RoleRequest roleRequest) {
        log.debug("[{}][addRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);
        RoleResponse roleResponse = roleService.add(language, roleRequest);

        log.debug("[{}][addRole] -> response: {}", this.getClass().getSimpleName(), roleResponse);

        return InternalApiResponse.<RoleResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ROLE_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(roleResponse)
                .build();
    }

    //Not: updateRole() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{language}/updateRole/{roleId}")
    public InternalApiResponse<RoleResponse> update(@PathVariable("language") Language language,
                                                    @Valid @RequestBody RoleRequest roleRequest,
                                                    @PathVariable("roleId") Long id) {
        log.debug("[{}][updateRole] -> request: {}", this.getClass().getSimpleName(), roleRequest);
        RoleResponse roleResponse = roleService.update(language, roleRequest);

        log.debug("[{}][updateRole] -> response: {}", this.getClass().getSimpleName(), roleResponse);

        return InternalApiResponse.<RoleResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ROLE_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(roleResponse)
                .build();
    }

    //Not: deleteRole() ******************************************************************************************************
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/deleteRole/{roleId}")
    public InternalApiResponse<RoleResponse> delete(@PathVariable("language") Language language,
                                                    @PathVariable("roleId") Long id) {
        log.debug("[{}][deleteRole] -> request: {}", this.getClass().getSimpleName(), id);
        RoleResponse roleResponse = roleService.softdelete(language, id);

        log.debug("[{}][deleteRole] -> response: {}", this.getClass().getSimpleName(), roleResponse);

        return InternalApiResponse.<RoleResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ROLE_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(roleResponse)
                .build();
    }
}
