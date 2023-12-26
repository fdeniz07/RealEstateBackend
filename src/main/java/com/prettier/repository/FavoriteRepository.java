package com.prettier.repository;

import com.prettier.entity.concretes.Favorite;
import com.prettier.entity.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    List<Favorite> findByUser(User authenticatedUser);

    List<Favorite> findByUserId(Long userId);

    Optional<Favorite> findByUserAndAdvertId(User authenticatedUser, Long advertId);

    void deleteByUser(User authenticatedUser);
}
