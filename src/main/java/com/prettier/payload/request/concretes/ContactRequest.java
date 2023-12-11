package com.prettier.payload.request.concretes;

import com.prettier.payload.request.abstracts.BaseEntityRequest;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactRequest extends BaseEntityRequest implements Serializable {

    @Column(name="first_name",nullable = false)
    @NotNull //!!! Test edilecek sonrasi icin
    @Size(min = 1, max = 30, message = "First name must not be empty")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your message must consist of the characters.")
    private String firstName;

    @Column(name="last_name",nullable = false)
    @Size(min = 1, max = 30, message = "Last name must not be empty")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your message must consist of the characters.")
    private String lastName;

    @Column(nullable = false)
    @Email(message = "Please, enter valid email address")
    @Size(max = 60)
    private String email;

    @Column(nullable = false)
    @Size(min = 10, max = 300, message = "Message must not be empty")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your message must consist of the characters.")
    private String message;

}
