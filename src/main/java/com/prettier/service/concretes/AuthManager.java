package com.prettier.service.concretes;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.payload.mapper.AuthMapper;
import com.prettier.payload.request.concretes.LoginRequest;
import com.prettier.payload.request.concretes.SignUpRequest;
import com.prettier.payload.response.concretes.LoginResponse;
import com.prettier.payload.response.concretes.SignUpResponse;
import com.prettier.repository.RoleRepository;
import com.prettier.repository.UserRepository;
import com.prettier.security.jwt.JWTUtils;
import com.prettier.service.abstracts.AuthService;
import com.prettier.service.abstracts.RoleService;
import com.prettier.shared.utils.enums.Language;
import com.prettier.shared.utils.validations.CheckUniqueFields;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final CheckUniqueFields checkUniqueFields;
    private final JWTUtils jwtService;
    private final AuthenticationManager authenticationManager;


    public SignUpResponse signUp(Language language, SignUpRequest signUpRequest) {

        log.debug("[{}][signUp] -> request: {}", this.getClass().getSimpleName(), signUpRequest);

        // Username, Email ve Phone var mi kontrolü
        if (!checkUniqueFields.checkDuplicate(language, signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPhone())) {

            // User user = authMapper.toUser(signUpRequest);
            User user = new User();
            user.setFirstName(signUpRequest.getFirstName());
            user.setLastName(signUpRequest.getLastName());
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPhone(signUpRequest.getPhone());
            user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setGender(signUpRequest.getGender());
            user.setBirthDate(signUpRequest.getBirthDate());
            user.setActivationToken(UUID.randomUUID().toString());

            Set<Role> defaultRole = roleService.getByRoleName("CUSTOMER");
            user.setRoles(defaultRole);

            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);

            log.debug("[{}][signUp] -> response: {}", this.getClass().getSimpleName(), jwtToken);
//        SignUpResponse response = authMapper.toResponse(newUser);
            return SignUpResponse.builder()
                    .token(jwtToken)
                    .id(user.getId())
                    .userName(user.getUsername())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .password(user.getPasswordHash())
                    .gender(user.getGender())
                    .birthDate(user.getBirthDate())
                    .createAt(user.getCreateAt())
                    .build();
        }
        return null;
    }

    @Override
    public LoginResponse login(Language language, LoginRequest loginRequest) {

        log.debug("[{}][login] -> request: {}", this.getClass().getSimpleName(), loginRequest);

        //!!! Gelen request'in icinden email ve password bilgisi aliniyor
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        //!!! authenticationManager üzerinden kullaniciyi valide ediyoruz
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );

        //!!! Valide edilen kullanici context'e atiliyor
        SecurityContextHolder.getContext().setAuthentication(authentication);


        //!!! GranteAuthority türündeki role yapisi String türüne ceviriliyor
        //authentication.getPrincipal() --> Anlik olarak login olan kullanicinin security katmanindaki userDetails bilgisini döndürür
        User userDetails = (User) authentication.getPrincipal();

        Set<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) //rolleri bizim anlayacagimiz türe(Admin,Manager...) ceviriyor
                .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst(); //Find gibi metotlar hata vermeye acik oldugu icin Option türünde alarak NullPointerException'unun önüne gecmis oluruz.

        //!!! JWT Token olusturuluyor
        var jwtToken = jwtService.generateToken(userDetails);
//        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        log.debug("[{}][login] -> response: {}", this.getClass().getSimpleName(), jwtToken);
        return LoginResponse.builder()
                .token(jwtToken)
//                .userName(loginRequest.getUserName())
//                .password(loginRequest.getPassword())
                .build();
    }
}

//{
//        "firstName":"user",
//        "lastName":"user1",
//        "username":"user1",
//        "email":"user1@mail.com",
//        "phone":"1111-111-1111",
//        "password":"P4ssword",
//        "gender":"MALE",
//        "birthDate":"01-05-1994"
//}
