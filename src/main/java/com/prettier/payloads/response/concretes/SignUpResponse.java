package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponse extends BaseEntityResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Set<RoleResponse> roles;
    private String token;
    private Gender gender;
    private LocalDate birthDate;

//    private Set<Role> rolesIds;


}
