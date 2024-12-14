package com.prettier.payloads.request.concretes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.enums.Gender;
import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SignUpRequest extends BaseEntityRequest {

    @NotNull //(message = "Please enter your firstname")
    @Size(min = 2, max = 30)//, message = "Your firstname should be at least 2 chars"
    private String firstName;

    @NotNull//(message = "Please enter your lastname")
    @Size(min = 2, max = 30)//, message = "Your lastname should be at least 2 chars"
    private String lastName;

    @NotNull//(message = "Please enter your email")
    @Email //(message = "Your email must be in the appropriate format.")
    @Size(min = 6, max = 80)//, message = "Your email must contain at least 6 characters and be in the appropriate format."
    private String email;

    @NotNull//(message = "Please enter your password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "Please enter your password as at least 8 chars") //
    private String password;

    @NotNull//(message = "Please enter your phone number")
//    @Size(min = 12, max = 12, message = "Phone number should be exact 12 chars")
//    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
//            message = "Please enter valid phone number")
    private String phone;

    @NotNull//(message = "Please enter your gender")
    private Gender gender;

    @NotNull//(message = "Please enter your birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive=true;

    public void setGender(Gender gender) {
        // Boş gelirse varsayılan değeri atayın
        if (gender == null) {
            this.gender = Gender.UNKNOWN;
        } else {
            this.gender = gender;
        }
    }
}
