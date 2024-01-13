package com.prettier.service.concretes;

import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.mapper.CategoryPropertyKeyMapper;
import com.prettier.payload.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.service.abstracts.CategoryPropertyKeyService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.categoryPropertyKey.CategoryPropertyKeyNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryPropertyKeyManager implements CategoryPropertyKeyService {

    private CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private CategoryPropertyKeyMapper categoryPropertyKeyMapper;


    //Not: getPropertiesByCategoryId() *************************************************************************************************************
    @Override
    public Set<CategoryPropertyKeyResponse> getPropertiesByCategoryId(Language language, Long categoryId) {

        log.debug("[{}][getProducts]", this.getClass().getSimpleName());
        Set<CategoryPropertyKey> categoryPropertyKeys = categoryPropertyKeyRepository.getCategoryPropertyKeyByCategory_Id(categoryId);

        if (categoryPropertyKeys.isEmpty()) {
            throw new CategoryPropertyKeyNotFoundException(language, FriendlyMessageCodes.CATEGORY_PROPERTY_KEY_NOT_FOUND_EXCEPTION, "CategoryPropertyKey not found");
        }

        log.debug("[{}][getCategoryPropertyKeys] -> response: {}", this.getClass().getSimpleName(), categoryPropertyKeys);
        Set<CategoryPropertyKeyResponse> categoryPropertyKeyResponseSet = categoryPropertyKeyMapper.toResponseSet(categoryPropertyKeys);

        return categoryPropertyKeyResponseSet;
    }
}
