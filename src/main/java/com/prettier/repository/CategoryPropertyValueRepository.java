package com.prettier.repository;

import com.prettier.entity.concretes.CategoryPropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPropertyValueRepository extends JpaRepository<CategoryPropertyValue,Long> {

}
