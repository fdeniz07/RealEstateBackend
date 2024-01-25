package com.prettier.security.service;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService { //Bu class Spring Security'e ait olan UserDetailsService interface'nin kendimize göre somutlastirdigimiz siniftir

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user != null) {

            //  Set<Role> roles = userRepository.findRolesByUserName(username);

            return new UserDetailsImp(
                    user.getId(),
                    user.getUserName(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getPasswordHash(),
                    user.getRoles()
            );
        } else {
            throw new UsernameNotFoundException("User '" + username + "'  not found: ");
        }
    }
}

