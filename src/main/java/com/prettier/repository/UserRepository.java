package com.prettier.repository;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //DB de kayitli email ve username var mi?
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

   // @Query("SELECT u FROM UserRole u WHERE u.id IN :id")//Verilen id ya da id lere göre user'lari getir
//   @Query("SELECT ur FROM User_Roles ur WHERE ur.role_id IN :roles")
//   Set<User> findByRoles_IdsEquals(Long roleId);

    //Optional<User> findByUsername(String username);

    //DB de kayitli email ve username var mi?
    //User findByEmail(String email);
    //User findByUsername(String username);

  // User findByActivationToken(String token);


}
