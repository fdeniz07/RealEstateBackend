package com.prettier.service.concretes;

import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payloads.mapper.CategoryPropertyKeyMapper;
import com.prettier.payloads.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.service.abstracts.CategoryPropertyKeyService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.categoryPropertyKey.CategoryPropertyKeyNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Service implementation class that manages Category Property Key operations.
 * This class handles the business logic for category property keys, including retrieval and management
 * of property keys associated with categories.
 * Implements the CategoryPropertyKeyService interface.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryPropertyKeyManager implements CategoryPropertyKeyService {

    private CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private CategoryPropertyKeyMapper categoryPropertyKeyMapper;


    //Not: getPropertiesByCategoryId() *************************************************************************************************************
    /**
     * Retrieves all property keys associated with a specific category.
     *
     * @param language The language for error messages and localization
     * @param categoryId The ID of the category whose property keys to retrieve
     * @return Set of CategoryPropertyKeyResponse containing the category's property keys
     * @throws CategoryPropertyKeyNotFoundException if no property keys are found for the category
     */
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
