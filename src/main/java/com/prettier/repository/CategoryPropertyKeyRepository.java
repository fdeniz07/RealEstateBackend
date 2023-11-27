package com.prettier.repository;

import com.prettier.entity.concretes.CategoryPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey,Long> {
}
