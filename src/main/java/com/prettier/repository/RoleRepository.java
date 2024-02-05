package com.prettier.repository;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.concretes.User;
import com.prettier.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = ?1") //PHQL olarak  sorgu olusturuldu (?1 ilk parametreyi getir (roleType))
    Role findByNameEquals(String name);

    //@Query("SELECT r FROM Role r WHERE r.name = ?1")
    boolean existsByNameEquals(String roleName);

//    Role findByRoleName(String roleName);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
   Set<Role> findAllById(Long id);

    boolean existsByName(String roleName);
    //Optional<Set<Role>> findByRoleName(String roleName);

}
