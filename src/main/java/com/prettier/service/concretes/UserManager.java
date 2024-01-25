package com.prettier.service.concretes;

import com.prettier.repository.UserRepository;
import com.prettier.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserManager implements UserService { //Bu sinif ile UserDetailImpl sinifimizi security'e tanitiyoruz

    private final UserRepository userRepository;

//    @Override
//    public long countAllAdmin() {
//
//        return userRepository.count();
//    }

//    @Override
//    public UserDetailsService userDetailsService() {
//
////        return username -> userRepository.findByUserName(username)
////                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
//        return userRepository::findByUserNameEquals;
//    }

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findByUserNameEquals(username);
//
//        if (user != null) {
//            return new UserDetailsImpl(user.getId(),
//                    user.getUserName(),
//                    user.getFirstName(),
//                    user.getLastName(),
//                    user.getEmail(),
//                    user.getPhone(),
//                    user.getPasswordHash(),
//                    String.valueOf(user.getRoles())
//            );
//        }
//        //TODO --> Security katmani icin exception handle class'i olusturulacak
//        throw new UsernameNotFoundException("User '" + username + "' not found"); //Spring Security'e ait bir exception
//    }
}
