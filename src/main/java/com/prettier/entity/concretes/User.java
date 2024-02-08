package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prettier.entity.abstracts.BaseEntity;
import com.prettier.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class User extends BaseEntity implements UserDetails {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "profile_image")
    private String image;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "user_info")
    private String userInfo;

    @Column(name = "password_hash", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // Client'den DB'ye giderken yazma islemi olsun, Db'den Client'e giderken Okuma islemi olmasin. Hassas veri oldugu icin okuma islemlerinde kullanilmaz
    @JsonIgnore //passwordu ön tarafa plaintext olarak gönderme
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{FriendlyMessageCodes__CONSTRAINT_PASSWORD_PATTERN}")
    //"Your password must consist of the characters a-z, A-Z, 0-9."
    private String passwordHash;

    @Column(name = "reset_password_code")
    private String resetPasswordCode; //hash

    private boolean builtIn;

    @JsonIgnore
    @Column(name = "is_active")
    private boolean active=false;


    private String activationToken;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Favorite> favoriteSet;


    @OneToMany(mappedBy = "guestUser")
    @ToString.Exclude
    private Set<TourRequest> tourRequestForGuestSet;

    @OneToMany(mappedBy = "ownerUser")
    @ToString.Exclude
    private Set<TourRequest> tourRequestForOwnerSet;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Log> logSet;

    @OneToMany(mappedBy = "user")
    private Set<Advert> advertSet;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Social> socials;

    // -----------RELATIONS -------------------------------------------------
    @ManyToMany(fetch = FetchType.EAGER)//(cascade = CascadeType.ALL,fetch = FetchType.EAGER) //
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;


    public User(Long id, String firstName, String lastName, String email, String phone, String passwordHash, Set<Role> roles) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passwordHash = passwordHash;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles) {

            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())); // Role'ü SimpleGrantedAuthority'ye dönüştürüp listeye ekleyin
        }
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (Role role : roles) {
//
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())); // Role'ü SimpleGrantedAuthority'ye dönüştürüp listeye ekleyin
//        }
//        this.authorities = grantedAuthorities;
        return authorities;
    }

    @Override
    public String getPassword() {

        return passwordHash;
//        return getPasswordHash();
    }

    @Override
    public String getUsername() {

        return email; //Biz email e göre eslestirme yapmayi tercih ediyoruz
//        return getUsername();
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

        User user = (User) o;
        return Objects.equals(getId(), user.getId()); // id ile kiyaslama
    }
}