package com.prettier.entity.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)//Auditing: You've included auditing information using @CreatedDate and @EntityListeners(AuditingEntityListener.class). This is useful for tracking when entities are created and updated.
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 30)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(min = 10, max = 80)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "reset_password_code")
    private String resetPasswordCode; //hash

    private boolean built_in;

    @Column(name = "create_at", nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_at")
    private LocalDateTime updateAt;
    // -----------RELATIONS -------------------------------------------------
//Relations with Sibling "roles" Table
//    @ToString.Exclude
//    @ManyToMany//
//    @JoinTable(
//            name = "user_roles"
//            ,joinColumns = @JoinColumn(name = "user_id")
//            ,inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roleSet ;
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