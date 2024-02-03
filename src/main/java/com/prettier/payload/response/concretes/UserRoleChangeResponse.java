package com.prettier.payload.response.concretes;

import com.prettier.entity.enums.Gender;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
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
public class UserRoleChangeResponse extends BaseEntityResponse {

    private String firstName;

    private String lastName;

    private String email;

    private Set<RoleResponse> roles;
}
