package com.prettier.service.concretes;

import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.mapper.CategoryMapper;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.request.concretes.CategoryUpdateRequest;
import com.prettier.payload.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.payload.response.concretes.CategoryResponse;
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

    @Override
    public CategoryResponse getByCategoryId(Language language, Long id) {

        log.debug("[{}][getCategory] -> request categoryId: {}", this.getClass().getSimpleName(), id);

        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Category not found for category id: " + id));

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), category);
        return categoryMapper.toResponse(category);
    }


    //Not: add() ****************************************************************************************************************************************
    
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
    @Override
    public Set<CategoryPropertyKeyResponse> getProperties(Language language, Long id) {

        log.debug("[{}][getProperties] -> request categoryId: {}", this.getClass().getSimpleName(), id);
        Set<CategoryPropertyKeyResponse> categoryPropertyKeyResponse = categoryPropertyKeyService.getPropertiesByCategoryId(language, id);

        log.debug("[{}][getProperties] -> response: {}", this.getClass().getSimpleName(), categoryPropertyKeyResponse);

        return  categoryPropertyKeyResponse;
    }






    //todo dtolar yapilsin
    public ResponseEntity<Set<CategoryPropertyKey>> getCategoryProperties(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });

        Set<CategoryPropertyKey> categoryProperties = category.getCategoryPropertyKeys();
        return ResponseEntity.ok(categoryProperties);

    }


    //todo categoryproportieskeyrepo kullanilacak
    public ResponseEntity<CategoryPropertyKey> createCategoryProperty(Long categoryId, CategoryPropertyKey categoryPropertyKey) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });
        categoryPropertyKey.setCategory(category);
        CategoryPropertyKey createdCategoryProperty = categoryPropertyKeyRepository.save(categoryPropertyKey);
        return ResponseEntity.ok(createdCategoryProperty);
    }

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

    //!!! Ilgili Id, Category tablosunda var mi kontrolü
    public Category getCategory(Language language, Long categoryId) {

        log.debug("[{}][getCategory] -> request categoryId: {}", this.getClass().getSimpleName(), categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Category not found for category id: " + categoryId));

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), category);
        return category;
    }

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