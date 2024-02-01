package com.prettier.payload.response.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.enums.Gender;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class UserResponse extends BaseEntityResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String phone;

    private Set<RoleResponse> roles;

    private Gender gender;

    private LocalDate birthDate;

    private boolean builtIn;

    private boolean isActive=true;
}
