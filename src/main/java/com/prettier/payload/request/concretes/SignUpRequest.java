package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest extends BaseEntityRequest {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
    private String phone;
    //private Set<Long> roleIds;
}
