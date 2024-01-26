package com.prettier.payload.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.entity.concretes.Role;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponse extends BaseEntityResponse {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private Set<RoleResponse> roles;
//    private Set<Role> rolesIds;


}
