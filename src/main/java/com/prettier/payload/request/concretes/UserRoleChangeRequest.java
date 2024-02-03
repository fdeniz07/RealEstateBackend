package com.prettier.payload.request.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRoleChangeRequest extends BaseEntityRequest {

    private String firstName;

    private String lastName;

    private String email;

    private Set<Role> roles;

}
