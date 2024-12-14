package com.prettier.payloads.response.concretes;

import com.prettier.entity.enums.Gender;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class UserResponse extends BaseEntityResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private Gender gender;

    private LocalDate birthDate;

}
