package com.prettier.repository;

import com.prettier.entity.concretes.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, String> {

    List<Language> findByActiveTrueOrderBySortOrderAscCodeAsc();

    List<Language> findByActiveTrueAndUiSupportedTrueOrderBySortOrderAscCodeAsc();

    List<Language> findByActiveTrueAndContentSupportedTrueOrderBySortOrderAscCodeAsc();

    List<Language> findByParent_CodeOrderBySortOrderAscCodeAsc(String parentCode);
}