package com.prettier.service.abstracts;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.request.concretes.RoleRequest;
import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.RoleResponse;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.shared.utils.enums.Language;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface UserService extends Serializable {

    UserResponse register(Language language, UserRequest userRequest);

    //Runner tarafi icin gerekli method
   long countAllAdmin();

    UserResponse login(Language language, UserRequest userRequest);

//    NOT: RoleService icin yazilan metotlar
//    public List<Role> getUserByIds(Long[] userIds);
//    public Set<Role> getRolesById(Set<Long> roleIds);

    //Runner tarafi icin gerekli method
//    UserResponse save(Language language, UserRequest userRequest);

}
