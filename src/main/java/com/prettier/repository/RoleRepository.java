package com.prettier.repository;

import com.prettier.entity.concretes.Role;
import com.prettier.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.id IN :id")
    Set<Role> findByIdsEquals(Long[] id);

//    @Query("SELECT r FROM Role r WHERE r.roleType = ?1") //PHQL olarak  sorgu olusturuldu (?1 ilk parametreyi getir (roleType))
//    Optional<Role> findByRoleEquals(RoleType roleType);

//    @Query("SELECT * (count(r)>0) FROM Role r WHERE r.roleType=?1")
//    boolean existsByRoleEquals(RoleType roleType);

   //boolean existsByRole_TypeEquals(RoleType roleType);

//    boolean existsByRole_NameEquals(String roleName);

//    Optional<Role> findByRoleTypeEquals(Role role);
//
//    @Query("SELECT ur FROM UserRole ur INNER JOIN ur.roles roles WHERE roles.role_id =?1")
//    List<Role> findAllByUserId(Long userId);
}
