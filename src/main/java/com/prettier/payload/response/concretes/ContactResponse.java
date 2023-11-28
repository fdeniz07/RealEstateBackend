package com.prettier.payload.response.concretes;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class ContactResponse implements Serializable {

    private String firstName;

    private String lastName;

    private String email;

    private String message;
}
