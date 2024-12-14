package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactRequest extends BaseEntityRequest{

    @NotNull(message = "Please enter your firstname")
    @Size(min = 1, max = 30, message = "Firstname must not be empty")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your firstname must consist of the characters.")
    private String firstName;

    @NotNull(message = "Please enter your lastname")
    @Size(min = 1, max = 30, message = "Lastname must not be empty")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your lastname must consist of the characters.")
    private String lastName;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please, enter valid email address")
    @Size(max = 60)
    private String email;

    @NotNull(message = "Please enter message")
    @Size(min = 10, max = 300, message = "Your icon slug should be between 10 and 300 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your message must consist of the characters.")
    private String message;
}
