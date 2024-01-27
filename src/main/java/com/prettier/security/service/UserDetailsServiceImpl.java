package com.prettier.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {  // implements UserDetailsService //Bu class Spring Security'e ait olan UserDetailsService interface'nin kendimize göre somutlastirdigimiz siniftir

//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByUserName(username);
//
//        if (user != null) {
//
//            //  Set<Role> roles = userRepository.findRolesByUserName(username);
//            return new UserDetailsImp(user);
////            return new UserDetailsImp(
////                    user.getId(),
////                    user.getUserName(),
////                    user.getFirstName(),
////                    user.getLastName(),
////                    user.getEmail(),
////                    user.getPhone(),
////                    user.getPasswordHash(),
////                    user.getRoles()
////            );
//        } else {
//            throw new UsernameNotFoundException("User '" + username + "'  not found: ");
//        }
//    }
}

