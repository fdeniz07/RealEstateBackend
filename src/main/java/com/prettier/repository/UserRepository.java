package com.prettier.repository;

import com.prettier.entity.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //DB de kayitli email ve username var mi?
    User findByEmail(String email);
    //User findByUsername(String username);

  // User findByActivationToken(String token);


}
