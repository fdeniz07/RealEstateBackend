package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 30)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 80)
    private String email;

    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 20)
    private String userName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // Client'den DB'ye giderken yazma islemi olsun, Db'den Client'e giderken Okuma islemi olmasin. Hassas veri oldugu icin okuma islemlerinde kullanilmaz
    @JsonIgnore //passwordu ön tarafa plaintext olarak gönderme
    // @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{FriendlyMessageCodes__CONSTRAINT_PASSWORD_PATTERN}")
    //"Your password must consist of the characters a-z, A-Z, 0-9."
    private String passwordHash;

    @Column(name = "reset_password_code")
    private String resetPasswordCode; //hash

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive = true;

    @JsonIgnore
    String activationToken;

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

    // -----------RELATIONS -------------------------------------------------

    @ManyToMany//(cascade = CascadeType.PERSIST)  // (fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public void addRole(Role role) {

        this.roles.add(role);
//        this.authorities.add(new SimpleGrantedAuthority(role.getName()));
    }



}