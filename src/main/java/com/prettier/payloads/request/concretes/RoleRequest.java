package com.prettier.payloads.request.concretes;

import com.prettier.payloads.request.abstracts.BaseEntityRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleRequest extends BaseEntityRequest {

    @NotNull(message = "Enter role name")
    @Size(max = 20, message = "Role name must be 20 chars")
    private String roleName;

    @NotNull(message = "Enter role description")
    @Size(max = 100, message = "Role description must be 100 chars")
    private String description;

    private boolean isActive=true;

    //Role name field ini DB ye her zaman büyük karakterlere dönüstürerek gönderiyoruz
   public void setRoleName(String roleName){

     this.roleName = roleName.toUpperCase();
   }

}
