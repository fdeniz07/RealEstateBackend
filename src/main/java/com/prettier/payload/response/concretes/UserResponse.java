package com.prettier.payload.response.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.payload.response.abstracts.BaseEntityResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseEntityResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String userName;

    private String phone;

    private boolean builtIn;

    private boolean isActive=true;
}
