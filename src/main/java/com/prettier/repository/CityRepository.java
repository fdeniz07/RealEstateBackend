package com.prettier.repository;

import com.prettier.entity.concretes.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

  boolean existsByName(String name);
}
