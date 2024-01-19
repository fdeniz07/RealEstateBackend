package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.enums.RoleType;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.shared.utils.enums.Language;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService extends Serializable {

    //Runner tarafi icin gerekli method
    public List<Role> getAllUserRole();

    //Runner tarafi icin gerekli method
    public RoleResponse save(Language language, RoleRequest roleRequest);

    public Role builtInSave(Language language, RoleType roleType);


    Set<Role> getRoleByIds(Long[] roleIds);


}
