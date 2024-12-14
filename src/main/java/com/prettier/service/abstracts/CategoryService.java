package com.prettier.service.abstracts;

import com.prettier.payloads.request.concretes.CategoryRequest;
import com.prettier.payloads.request.concretes.CategoryUpdateRequest;
import com.prettier.payloads.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.payloads.response.concretes.CategoryResponse;
import com.prettier.shared.utils.enums.Language;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Set;

public interface CategoryService extends Serializable {

    Page<CategoryResponse> getCategoriesByActive(Language language, int page, int size, String sort, String type);

    Page<CategoryResponse> getCategories(Language language, int page, int size, String sort, String type);

    CategoryResponse getByCategoryId(Language language, Long id);

    CategoryResponse add(Language language, CategoryRequest categoryRequest);

    CategoryResponse update(Language language, CategoryUpdateRequest categoryUpdateRequest, Long id);

    CategoryResponse softDelete(Language language, Long id);

    Set<CategoryPropertyKeyResponse> getProperties(Language language, Long id);
}
