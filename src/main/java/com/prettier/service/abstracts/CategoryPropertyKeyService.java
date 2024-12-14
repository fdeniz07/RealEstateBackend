package com.prettier.service.abstracts;

import com.prettier.payloads.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.shared.utils.enums.Language;

import java.io.Serializable;
import java.util.Set;

public interface CategoryPropertyKeyService extends Serializable {

    Set<CategoryPropertyKeyResponse> getPropertiesByCategoryId(Language language, Long categoryId);
}
