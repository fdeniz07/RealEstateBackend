package com.prettier.payloads.response.concretes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.prettier.payloads.response.abstracts.BaseEntityResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse extends BaseEntityResponse{

    private String firstName;

    private String lastName;

    private String email;

    private String message;
}
