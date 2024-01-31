package com.prettier.controller;

import com.prettier.payload.mapper.RoleMapper;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Role", description = "Prettier Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/roles")
@Slf4j //Log eklemek icin kullaniyoruz
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    //Not: addRole() ******************************************************************************************************
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/addRole")
    public InternalApiResponse<RoleResponse> add(@PathVariable("language") Language language,
                                                          @RequestBody RoleRequest roleRequest) {
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


}
