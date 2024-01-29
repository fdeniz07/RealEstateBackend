package com.prettier.security.service;

import com.prettier.entity.concretes.User;
import com.prettier.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {  // //Bu class Spring Security'e ait olan UserDetailsService interface'nin kendimize göre somutlastirdigimiz siniftir

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User email not found"));

        if (user != null) {

            //  Set<Role> roles = userRepository.findRolesByUserName(username);
//            return new User(user);
            return new User(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPhone(),
                    user.getPasswordHash(),
                    user.getRoles()
            );
        } else {
            throw new UsernameNotFoundException("User email '" + username + "'  not found: ");
        }
    }
}

