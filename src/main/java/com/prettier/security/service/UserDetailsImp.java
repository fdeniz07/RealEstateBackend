package com.prettier.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.concretes.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImp implements UserDetails { //Bu class bizim user class'imizin, SpringSecurity deki UserDetails interface'inin somutlastirilmasidir.

    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    @JsonIgnore //passwordu ön tarafa plaintext olarak gönderme
    private String password;

    private Role role;

    private Collection<? extends GrantedAuthority> authorities;

    //GrantedAuthority'i constructor olarak almiyor, bizdeki role'ü GrantedAuthority'e dönüstürüyoruz
//    public UserDetailsImp(Long id, String userName, String firstName, String lastName, String email, String phone, String password, Collection<? extends GrantedAuthority> authorities) {
//        this.id = id;
//        this.userName = userName;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phone = phone;
//        this.password = password;
//        this.authorities = authorities;
////        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
////        grantedAuthorities.add(new SimpleGrantedAuthority(role));
////        for (Role role : roles) {
////            // Role'ü SimpleGrantedAuthority'ye dönüştürüp listeye ekleyin
////            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
////        }
////        //grantedAuthorities.add(new SimpleGrantedAuthority(role)); //String türünde Role olsaydi
//
//    }

    public UserDetailsImp(Long id, String userName, String firstName, String lastName, String email, String phone ,String password, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        for (Role role : roles) {
            // Role'ü SimpleGrantedAuthority'ye dönüştürüp listeye ekleyin
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return authorities;
//    }

        @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    //iki tane userdetails türünde nesne gelecekse ve birbiriyle kiyaslanacaksa ve bu kiyaslanma kriterini kendimizi göre özellestireceksek;
    public boolean equals(Object o) {

        if (this == o)//kendisi ile kiyasliyorsak
            return true;

        if (o == null || getClass() != o.getClass()) //iki farkli objeyi karsilastiriyoruz, ayni degeri döndürüp döndürmedigini kiyasliyoruz
            return false;

        UserDetailsImp user = (UserDetailsImp) o;
        return Objects.equals(id, user.id); // id ile kiyaslama
    }
}
