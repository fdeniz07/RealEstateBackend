package com.prettier.repository;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //DB de kayitli email var mi?
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Page<User> findByActiveTrue(Pageable pageable);

    Page<User> findByActiveFalse(Pageable pageable);


    //Role adina göre User üzerinden Roles ara tabloya git, Role tablosundaki name alanina esit degerdeki kullanicilari getir
    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name =:roleName")
    Page<User> getUsersByRoleName(@Param("roleName") String roleName, Pageable pageable);


    @Query("SELECT DISTINCT r FROM User u JOIN u.roles r WHERE u.id = :userId")
    Set<Role> getRolesByUserId(@Param("userId") Long userId);


    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT u.email FROM User u WHERE u.id =?1")
    String findEmailById(Long id);

   // boolean existsByName(String userName);


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
