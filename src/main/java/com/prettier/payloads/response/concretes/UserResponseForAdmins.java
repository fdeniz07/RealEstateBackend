package com.prettier.payloads.response.concretes;

import com.prettier.entity.enums.Gender;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
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
public class UserResponseForAdmins extends BaseEntityResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Set<RoleResponse> roles;

    private Gender gender;

    private LocalDate birthDate;

    private String image;

    private String userInfo;

    private String resetPasswordCode;

    private boolean builtIn;

    private boolean active;

    private String activationToken;
}
