package com.prettier.repository;

import com.prettier.entity.concretes.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
