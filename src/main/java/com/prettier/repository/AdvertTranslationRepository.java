package com.prettier.repository;

import com.prettier.entity.concretes.AdvertTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdvertTranslationRepository extends JpaRepository<AdvertTranslation, Long> {

    @Query(
            "SELECT CASE WHEN COUNT(at) > 0 THEN true ELSE false END "
                    + "FROM AdvertTranslation at "
                    + "WHERE at.language.code = :languageCode AND LOWER(at.slug) = LOWER(:slug)")
    boolean existsByLanguageCodeAndSlugIgnoreCase(
            @Param("languageCode") String languageCode, @Param("slug") String slug);
}