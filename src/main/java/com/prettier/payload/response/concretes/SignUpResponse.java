package com.prettier.payload.response.concretes;

import com.prettier.payload.response.abstracts.BaseEntityResponse;

import java.util.Set;

public class SignUpResponse extends BaseEntityResponse {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private Set<RoleResponse> roles;
}
