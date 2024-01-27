package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.RoleType;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.request.concretes.RoleUpdateRequest;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.shared.utils.enums.Language;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService extends Serializable {

    //NOT: *********** Data Inilitalizer icin gerekli metotlar *************************

    List<Role> getAllUserRole();

   Set<Role> getByRoleName(String roleName);

   Set<Role> getByUser(User user);

    //NOT: *********** Role Manager standart metotlar *************************************

    List<Role> getRoles();

    RoleResponse getById(Language language, Long id);

    RoleResponse save(Language language, RoleRequest roleRequest);

    RoleResponse add(Language language, RoleRequest roleRequest);

    RoleResponse update(Language language, RoleUpdateRequest roleUpdateRequest);

    RoleResponse softdelete(Language language,  Long id);

}