package com.prettier.payload.response.concretes;

import com.prettier.payload.response.abstracts.BaseEntityResponse;
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
public class ContactResponse extends BaseEntityResponse{

    private String firstName;

    private String lastName;

    private String email;

    private String message;
}
