package com.prettier.repository;

import com.prettier.entity.concretes.CategoryPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey,Long> {

    Set<CategoryPropertyKey> getCategoryPropertyKeyByCategory_Id(Long categoryId);
}
