package com.prettier.repository;

import com.prettier.entity.concretes.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Advert getByIdAndIsActiveFalse(Long id);
//
//    List<Advert> getAllByIsActiveFalse();

    @Query("SELECT a FROM Advert a WHERE a.isActive = true")
    Page<Advert> findAllActive(Pageable pageable);

}
