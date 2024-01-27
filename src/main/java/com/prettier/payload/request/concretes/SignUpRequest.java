package com.prettier.payload.request.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SignUpRequest extends BaseEntityRequest {

    @NotNull(message = "Please enter your firstname")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull(message = "Please enter your lastname")
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull(message = "Please enter your username")
    @Size(min = 5, max = 20)
    private String username;

    @NotNull(message = "Please enter your email")
    @Email (message = "Please verify your email")
    @Size(min = 10, max = 80)
    private String email;

    @NotNull(message = "Please enter your password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{FriendlyMessageCodes__CONSTRAINT_PASSWORD_PATTERN}") //
    private String password;

    @NotNull(message = "Please enter your phone number")
    private String phone;

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive=true;
}
