package com.prettier.service.abstracts;

import com.prettier.payload.request.concretes.UserRequest;
import com.prettier.payload.response.concretes.UserResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;

public interface UserService extends Serializable {

//    UserResponse register(Language language, UserRequest userRequest);
//
    //Runner tarafi icin gerekli method
//    long countAllAdmin();
//
//    UserResponse login(Language language, UserRequest userRequest);
//
//    Page<UserResponse> getUsers(Language language, int page, int size, String sort, String type);
//
////    NOT: RoleService icin yazilan metotlar
////    public List<Role> getUserByIds(Long[] userIds);
////    public Set<Role> getRolesById(Set<Long> roleIds);
//
//    //Runner tarafi icin gerekli method
////    UserResponse save(Language language, UserRequest userRequest);

//    UserDetailsService userDetailsService();
}
