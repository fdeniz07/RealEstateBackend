package com.prettier.payload.request.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRequest extends BaseEntityRequest {

    @NotNull(message = "Please enter your firstname")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull(message = "Please enter your lastname")
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull(message = "Please enter your email")
    @Size(min = 10, max = 80)
    private String email;

    @NotNull(message = "Please enter your username")
    @Size(min = 8, max = 20)
    private String userName;

    @NotNull(message = "Please enter your phone number")
    private String phone;

    @NotNull(message = "Please enter your password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{FriendlyMessageCodes__CONSTRAINT_PASSWORD_PATTERN}") //"Your password must consist of the characters a-z, A-Z, 0-9."
    private String passwordHash;

//    private Set<Long> roleIds;


}
