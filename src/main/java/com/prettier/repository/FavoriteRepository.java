package com.prettier.repository;

import com.prettier.entity.concretes.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
}
