
package com.prettier.security.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class UserDetailsImp  { //implements UserDetails // //Bu class bizim user class'imizin, SpringSecurity deki UserDetails interface'inin somutlastirilmasidir.

//    private User user;

//    private Long id;
//
//    private String userName;
//
//    private String firstName;
//
//    private String lastName;
//
//    private String email;
//
//    private String phone;
//
//    @JsonIgnore //passwordu ön tarafa plaintext olarak gönderme
//    private String password;
//
//    private Set<Role> roles;
//
//    private Collection<? extends GrantedAuthority> authorities;
//
////    public UserDetailsImp(User user) {
////
////    }
//
//    //GrantedAuthority'i constructor olarak almiyor, bizdeki role'ü GrantedAuthority'e dönüstürüyoruz
//    public UserDetailsImp(Long id, String userName, String firstName, String lastName, String email, String phone, String password,Set<Role> roles ) {
//        this.id = id;
//        this.userName = userName;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phone = phone;
//        this.password = password;
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (Role role : roles) {
//            // Role'ü SimpleGrantedAuthority'ye dönüştürüp listeye ekleyin
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        this.authorities = grantedAuthorities;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
////
////        Set<Role> roles = user.getRoles();
////
////        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
////
////        for (Role role : roles) {
////
////            authorities.add(new SimpleGrantedAuthority(role.getName()));
////        }
//
//        return authorities;
//        //  return List.of(new SimpleGrantedAuthority(role.getName()));
//    }
//
//    @Override
//    public String getPassword() {
//
//         return password;
////        return user.getPasswordHash();
//    }
//
//    @Override
//    public String getUsername() {
//
//        return userName;
////        return user.getUserName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//
//        return true;
//    }
//
//    //iki tane userdetails türünde nesne gelecekse ve birbiriyle kiyaslanacaksa ve bu kiyaslanma kriterini kendimizi göre özellestireceksek;
//    public boolean equals(Object o) {
//
//        if (this == o)//kendisi ile kiyasliyorsak
//            return true;
//
//        if (o == null || getClass() != o.getClass()) //iki farkli objeyi karsilastiriyoruz, ayni degeri döndürüp döndürmedigini kiyasliyoruz
//            return false;
//
//        UserDetailsImp user = (UserDetailsImp) o;
//        return Objects.equals(id, user.id); // id ile kiyaslama
//    }
}
