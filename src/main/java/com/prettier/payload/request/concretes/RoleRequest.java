package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
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
public class RoleRequest extends BaseEntityRequest {

    @NotNull(message = "Select your role name")
    private String roleName;

    private String description;

    private Set<Long> userIds; // TODO : Set ?
}
