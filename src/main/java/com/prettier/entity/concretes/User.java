package com.prettier.entity.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.prettier.entity.abstracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public class User extends BaseEntity implements Serializable {

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
    @Size(min = 8, max = 20)
    private String userName;

    @Column(nullable = false)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "reset_password_code")
    private String resetPasswordCode; //hash

    @JsonIgnore
    private boolean builtIn;

    @JsonIgnore
    private boolean isActive=true;

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


    // -----------RELATIONS -------------------------------------------------
//Relations with Sibling "roles" Table

    @ManyToMany//
    @JoinTable(
            name = "user_roles"
            ,joinColumns = @JoinColumn(name = "user_id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<Role> roleSet ;
//
//    //Relation with Child Advert
//    @OneToMany(mappedBy = "user",targetEntity = Advert.class, fetch = FetchType.EAGER)
//    private Set<Advert> advertSet;
//
//    // Relation with Child "tour_requests"  ownerUser
//    @OneToMany(mappedBy = "ownerUser", fetch = FetchType.EAGER)
//    private Set<TourRequest> tourRequestSetForOwner;
//    // Relation with Child "tour_requests"  ownerUser
//    @OneToMany(mappedBy = "guestUser", fetch = FetchType.EAGER)
//    private Set<TourRequest> tourRequestSetForGuest;
//    // Relation with Child logs Table
//    @OneToMany(mappedBy = "user", targetEntity = Log.class, fetch = FetchType.EAGER)
//    private Set<Log> logSet;
//

}