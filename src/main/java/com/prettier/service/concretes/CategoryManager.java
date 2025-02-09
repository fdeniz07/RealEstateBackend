package com.prettier.service.concretes;

import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payloads.mapper.CategoryMapper;
import com.prettier.payloads.request.concretes.CategoryRequest;
import com.prettier.payloads.request.concretes.CategoryUpdateRequest;
import com.prettier.payloads.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.payloads.response.concretes.CategoryResponse;
import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.repository.CategoryPropertyValueRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.service.abstracts.CategoryPropertyKeyService;
import com.prettier.service.abstracts.CategoryService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.categories.CategoryAlreadyDeletedException;
import com.prettier.shared.exception.exceptions.categories.CategoryAlreadyExistsException;
import com.prettier.shared.exception.exceptions.categories.CategoryNotCreatedException;
import com.prettier.shared.exception.exceptions.categories.CategoryNotFoundException;
import com.prettier.shared.utils.enums.Language;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation class that manages Category-related operations.
 * This class handles CRUD operations, pagination, and property management for Categories.
 * Implements the CategoryService interface to provide category management functionality.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryManager implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;



    //Not: getAllWithActives() *********************************************************************************************************************************
    /**
     * Retrieves a paginated list of active categories.
     *
     * @param language The language for error messages and localization
     * @param page The page number for pagination
     * @param size The number of items per page
     * @param sort The field to sort by
     * @param type The sort direction ("asc" or "desc")
     * @return A Page of CategoryResponse containing active categories
     * @throws CategoryNotFoundException if no categories are found
     */
    @Override
    public Page<CategoryResponse> getCategoriesByActive(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getCategories]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<CategoryResponse> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Categories not found");
        }
        log.debug("[{}][getCategories] -> response: {}", this.getClass().getSimpleName(), categories);
        return categoryRepository.findByActiveEquals(pageable).map(categoryMapper::toResponse);
    }


    //Not: getAll() *********************************************************************************************************************************
    /**
     * Retrieves a paginated list of all categories.
     *
     * @param language The language for error messages and localization
     * @param page The page number for pagination
     * @param size The number of items per page
     * @param sort The field to sort by
     * @param type The sort direction ("asc" or "desc")
     * @return A Page of CategoryResponse containing all categories
     * @throws CategoryNotFoundException if no categories are found
     */
    @Override
    public Page<CategoryResponse> getCategories(Language language, int page, int size, String sort, String type) {

        log.debug("[{}][getCategories]", this.getClass().getSimpleName());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            PageRequest.of(page, size, Sort.by(sort).descending());
        }

        List<CategoryResponse> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Categories not found");
        }
        log.debug("[{}][getCategories] -> response: {}", this.getClass().getSimpleName(), categories);
        return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
    }


    //Not: getById() *********************************************************************************************************************************
    /**
     * Retrieves a specific category by its ID.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the category to retrieve
     * @return CategoryResponse containing the category details
     * @throws CategoryNotFoundException if the category is not found
     */
    @Override
    public CategoryResponse getByCategoryId(Language language, Long id) {

        log.debug("[{}][getCategory] -> request categoryId: {}", this.getClass().getSimpleName(), id);

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Category not found for category id: " + id));

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), category);
        return categoryMapper.toResponse(category);
    }


    //Not: add() ****************************************************************************************************************************************
    /**
     * Creates a new category.
     *
     * @param language The language for error messages and localization
     * @param categoryRequest The category creation request containing category details
     * @return CategoryResponse containing the created category details
     * @throws CategoryNotCreatedException if the category cannot be created
     * @throws CategoryAlreadyExistsException if a category with the same title already exists
     */
    @Override
    public CategoryResponse add(Language language, CategoryRequest categoryRequest) {

        log.debug("[{}][createCategory] -> request: {}", this.getClass().getSimpleName(), categoryRequest);
        //Requestten gelen CityId var mi diye kontrol ediyoruz, varsa bilgileri atiyoruz
        //City existingCity = cityService.getByCityId(language, categoryRequest.getCityId());

        // Category adi veritabaninda mevcut mu kontrolü
        boolean existsByCategoryTitle = existsByCategoryTitle(language, categoryRequest.getTitle());

        //Category db de mevcutsa hata firlat, yoksa kaydet
        if (existsByCategoryTitle) {
            throw new CategoryNotCreatedException(language, FriendlyMessageCodes.CATEGORY_NOT_CREATED_EXCEPTION, "category request: " + categoryRequest.toString());
        } else {

            //Mapping isleminden sonra daha önce buldugumuz city bilgisini set edip, kaydediyoruz
            Category newCategory = categoryMapper.toCategory(categoryRequest);
            //newCategory.setCity(existingCity);

            Category response = categoryRepository.save(newCategory);
            log.debug("[{}][createCategory] -> response: {}", this.getClass().getSimpleName(), response);
            return categoryMapper.toResponse(response);
        }
    }


    //Not: update() *********************************************************************************************************************************
    /**
     * Updates an existing category.
     *
     * @param language The language for error messages and localization
     * @param categoryUpdateRequest The category update request containing updated details
     * @param id The ID of the category to update
     * @return CategoryResponse containing the updated category details
     * @throws CategoryNotFoundException if the category is not found
     */
    @Override
    public CategoryResponse update(Language language, CategoryUpdateRequest categoryUpdateRequest, Long id) {

        log.debug("[{}][updateCategory] -> request: {} {}", this.getClass().getSimpleName(), id, categoryUpdateRequest);

        //Requestten gelen CityId var mi diye kontrol ediyoruz, varsa bilgileri atiyoruz
        //City existingCity = cityService.getByCityId(language, categoryUpdateRequest.getCityId());

        //Category gercekte db de var mi kontrolü
        Category existingCategory = getCategory(language, id);

        //Mapping islemini yap, sonrasinda bulunan city bilgilerini set et
        Category updatedCategory = categoryMapper.toUpdatedCategory(categoryUpdateRequest, existingCategory);
        updatedCategory.setId(existingCategory.getId());

        // Veritabanına güncellenmiş Category'yi kaydet
        Category response = categoryRepository.save(updatedCategory);

        log.debug("[{}][updateCategory] -> response: {}", this.getClass().getSimpleName(), response);
        return categoryMapper.toResponse(response);
    }


    //Not: delete() *********************************************************************************************************************************
    /**
     * Performs a soft delete on a category by marking it as deleted.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the category to delete
     * @return CategoryResponse containing the deleted category details
     * @throws CategoryNotFoundException if the category is not found
     * @throws CategoryAlreadyDeletedException if the category is already deleted
     */
    @Override
    public CategoryResponse softDelete(Language language, Long id) {

        //Category Var mi kontrolü
        log.debug("[{}][deleteCategory] -> request categoryId: {}", this.getClass().getSimpleName(), id);
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Category not found for category id: " + id));

        //Category var ama isDeleted=true mu kontrolü
        try {
            category = getCategory(language, id);
            category.setDeleted(true);
            CategoryResponse categoryResponse = categoryMapper.toResponse(categoryRepository.save(category));
            log.debug("[{}][deleteCategory] -> response: {}", this.getClass().getSimpleName(), categoryResponse);
            return categoryResponse;
        } catch (CategoryNotFoundException categoryNotFoundException) {
            throw new CategoryAlreadyDeletedException(language, FriendlyMessageCodes.CATEGORY_ALREADY_DELETED, "Category already deleted category id: " + id);
        }
    }


    //Not: getProperties() *************************************************************************************************************
    /**
     * Retrieves all properties associated with a specific category.
     *
     * @param language The language for error messages and localization
     * @param id The ID of the category whose properties to retrieve
     * @return Set of CategoryPropertyKeyResponse containing the category's properties
     */
    @Override
    public Set<CategoryPropertyKeyResponse> getProperties(Language language, Long id) {

        log.debug("[{}][getProperties] -> request categoryId: {}", this.getClass().getSimpleName(), id);
        Set<CategoryPropertyKeyResponse> categoryPropertyKeyResponse = categoryPropertyKeyService.getPropertiesByCategoryId(language, id);

        log.debug("[{}][getProperties] -> response: {}", this.getClass().getSimpleName(), categoryPropertyKeyResponse);

        return  categoryPropertyKeyResponse;
    }






    //todo dtolar yapilsin
    /**
     * Retrieves category properties with basic error handling.
     *
     * @param categoryId The ID of the category whose properties to retrieve
     * @return ResponseEntity containing a Set of CategoryPropertyKey
     * @throws ResourceAccessException if the category is not found
     */
    public ResponseEntity<Set<CategoryPropertyKey>> getCategoryProperties(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });

        Set<CategoryPropertyKey> categoryProperties = category.getCategoryPropertyKeys();
        return ResponseEntity.ok(categoryProperties);

    }


    //todo categoryproportieskeyrepo kullanilacak
    /**
     * Creates a new property for a specific category.
     *
     * @param categoryId The ID of the category to create the property for
     * @param categoryPropertyKey The property to create
     * @return ResponseEntity containing the created CategoryPropertyKey
     * @throws ResourceAccessException if the category is not found
     */
    public ResponseEntity<CategoryPropertyKey> createCategoryProperty(Long categoryId, CategoryPropertyKey categoryPropertyKey) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });
        categoryPropertyKey.setCategory(category);
        CategoryPropertyKey createdCategoryProperty = categoryPropertyKeyRepository.save(categoryPropertyKey);
        return ResponseEntity.ok(createdCategoryProperty);
    }


    /**
     * Updates an existing category property.
     *
     * @param propertyKeyId The ID of the property to update
     * @param updatedProperty The updated property details
     * @return ResponseEntity containing the updated CategoryPropertyKey
     * @throws ResourceAccessException if the property is not found
     */
    public ResponseEntity<CategoryPropertyKey> updateCategoryProperty(Long propertyKeyId, CategoryPropertyKey updatedProperty) {

        CategoryPropertyKey existingProperty = categoryPropertyKeyRepository.findById(propertyKeyId).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });

        if (existingProperty.isBuiltIn()) {
            return new ResponseEntity<>(existingProperty, HttpStatus.NOT_FOUND);
        }
        existingProperty.setName(updatedProperty.getName());
        existingProperty.setBuiltIn(updatedProperty.isBuiltIn());
        // todo category ve proporty value setlenecek

        CategoryPropertyKey updatedPropertyKey = categoryPropertyKeyRepository.save(existingProperty);
        return ResponseEntity.ok(updatedPropertyKey);

    }


    /**
     * Deletes a category property.
     *
     * @param propertyId The ID of the property to delete
     * @return ResponseEntity containing the deleted CategoryPropertyKey
     * @throws ResourceAccessException if the property is not found
     */
    public ResponseEntity<CategoryPropertyKey> deleteCategoryProperty(Long propertyId) {
        CategoryPropertyKey existingProperty = categoryPropertyKeyRepository.findById(propertyId).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });

        if (existingProperty.isBuiltIn()) {
            return new ResponseEntity<>(existingProperty, HttpStatus.NOT_FOUND);
        }
        //todo Delete related category_property_values
        //categoryPropertyValueRepository.deleteRelated(propertyId);

        categoryPropertyKeyRepository.delete(existingProperty);
        return ResponseEntity.ok(existingProperty);

    }


    //Not: Other *********************************************************************************************************************************
    /**
     * Helper method to retrieve a category by ID.
     *
     * @param language The language for error messages and localization
     * @param categoryId The ID of the category to retrieve
     * @return Category entity
     * @throws CategoryNotFoundException if the category is not found
     */
    //!!! Ilgili Id, Category tablosunda var mi kontrolü
    public Category getCategory(Language language, Long categoryId) {

        log.debug("[{}][getCategory] -> request categoryId: {}", this.getClass().getSimpleName(), categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Category not found for category id: " + categoryId));

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), category);
        return category;
    }



    /**
     * Helper method to check if a category with the given title exists.
     *
     * @param language The language for error messages and localization
     * @param categoryTitle The title to check
     * @return boolean indicating whether the category exists
     * @throws CategoryAlreadyExistsException if a category with the given title exists
     */
    //!!! Ilgili CategoryName, Category tablosunda var mi kontrolü
    public boolean existsByCategoryTitle(Language language, String categoryTitle) {

        log.debug("[{}][getCategory] -> request categoryName: {}", this.getClass().getSimpleName(), categoryTitle);
        if (categoryRepository.existsByTitle(categoryTitle)) {
            throw new CategoryAlreadyExistsException(language, FriendlyMessageCodes.CATEGORY_ALREADY_EXISTS, "This Category already exists for category title: " + categoryTitle);
        }

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), categoryTitle);
        return false;
    }
    
    

//    private Category updatedCategory(Long id, CategoryRequest categoryRequest) {
//        return Category.builder()
//                .id(id)
//               .categorySet(categoryRequest.getCategorySet())
//                .seq(categoryRequest.getSeq())
//                .icon(categoryRequest.getIcon())
//                .slug(categoryRequest.getSlug())
//                .title(categoryRequest.getTitle())
//                .builtIn(categoryRequest.isBuiltIn())
//                .createAt(categoryRequest.getCreateAt())
//                .active(categoryRequest.isActive())
//                .categoryPropertyKeys(categoryRequest.getCategoryPropertyKeys())
//                .updateAt(categoryRequest.getUpdateAt())
//                //todo
//                .build();
//    }


    //class
}