package com.prettier.repository;

import com.prettier.entity.concretes.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Advert getByIdAndIsActiveFalse(Long id);
//
//    List<Advert> getAllByIsActiveFalse();

    @Query("SELECT a FROM Advert a WHERE a.isActive = true")
    Page<Advert> findAllActive(Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Advert a WHERE LOWER(a.slug) = LOWER(:slug)")
    boolean existsBySlugIgnoreCase(@Param("slug") String slug);

}
