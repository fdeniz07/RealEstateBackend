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

      //@Column(name = "password_hash", nullable = false)
    @NotNull(message = "Please enter your password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",message = "{FriendlyMessageCodes__CONSTRAINT_PASSWORD_PATTERN}") //"Your password must consist of the characters a-z, A-Z, 0-9."
    private String passwordHash;

   // @JsonIgnore
    private Set<Long> roleIds;


    //@JsonIgnore
    private boolean builtIn;

    //@JsonIgnore
    private boolean isActive=true;

    //Eğer bu metodun bir Set döndürmesi gerekiyorsa, bu metodun başlangıcında bir Set örneği oluşturduğunuzdan ve döndürdüğünüzden emin olun.
//    public Set<Long> getRoleIds() {
//        if (this.roleIds == null) {
//            this.roleIds = new HashSet<>();
//        }
//        return this.roleIds;
//    }
    //Yukarıdaki örnekte, getRoleIds() metodu, eğer roleIds örneği null ise bir HashSet oluşturarak bunu döndürüyor. Böylece, herhangi bir yerde bu metodu çağırdığınızda null bir değerle karşılaşmamış olacaksınız. Eğer başka bir şekilde roleIds set'inin oluşturulduğundan eminseniz, sadece getRoleIds() metodunu düzeltebilirsiniz.

}
