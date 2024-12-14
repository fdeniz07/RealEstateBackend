package com.prettier.payloads.response.concretes;

import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
