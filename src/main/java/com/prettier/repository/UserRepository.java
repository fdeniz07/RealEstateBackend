package com.prettier.repository;

import com.prettier.entity.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //DB de kayitli email ve username var mi?
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    //    Optional<User> findByUserName(String userName);

    // Optional<User> findByUserName(@Param("username") String userName);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);


//@Query("Select r.* from users u join user_roles ur on u.id = ur.user_id join roles r on ur.role_id = r.id where u.user_name =:username")
//Set<Role> findRolesByUserName(@Param("username") String username);
//  JPQL
//    @Query("SELECT r FROM User u JOIN u.roles r WHERE u.username = :username")
//    Set<Role> findRolesByUsername(@Param("username") String username);


//    HQL
//    @Query("SELECT r FROM User u JOIN u.roles r WHERE u.username = :username")
//    Set<Role> findRolesByUserName(String username);
    //Set<Role> findRolesByUsername(@Param("username") String username);


    // User findByRole(Role role);


    // @Query("SELECT u FROM UserRole u WHERE u.id IN :id")//Verilen id ya da id lere göre user'lari getir
//   @Query("SELECT ur FROM User_Roles ur WHERE ur.role_id IN :roles")
//   Set<User> findByRoles_IdsEquals(Long roleId);

    //Optional<User> findByUsername(String username);

    //DB de kayitli email ve username var mi?
    //User findByEmail(String email);
    //User findByUsername(String username);

    // User findByActivationToken(String token);


}
