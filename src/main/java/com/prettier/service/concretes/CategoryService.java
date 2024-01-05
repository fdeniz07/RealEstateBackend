package com.prettier.service.concretes;

import com.prettier.entity.concretes.Advert;
import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.mapper.CategoryMapper;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.request.concretes.CategoryUpdateRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.repository.CategoryPropertyValueRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.service.abstracts.CategoryPropertyKeyService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.exception.exceptions.categories.CategoryNotFoundException;
import com.prettier.shared.utils.enums.Language;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

    public Page<CategoryResponse> getIsActiveWithPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return categoryRepository.findByActiveEquals(pageable).map(categoryMapper::toResponse);


    }

    public Page<CategoryResponse> getAllWithPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);


    }


    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });
        return categoryMapper.toResponse(category);
    }

    public ResponseEntity add(CategoryRequest categoryRequest) {

        Category category = categoryMapper.toCategory(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(HttpStatus.CREATED);


    }

    public Category update(Language language, CategoryUpdateRequest categoryUpdateRequest, Long id) {

        log.debug("[{}][updateCategory] -> request: {} {}", this.getClass().getSimpleName(), id, categoryUpdateRequest);

        if (!categoryRepository.existsById(id)) {
            throw new ResourceAccessException("");//todo  ex olustur
        }

        //Category Var mi kontrolü
        Category existingCategory = getCategory(language, id);

        Category updatedCategory=  categoryMapper.toUpdatedCategory(categoryUpdateRequest, id,existingCategory);
        categoryRepository.save(updatedCategory);
        log.debug("[{}][updateCategory] -> response: {}", this.getClass().getSimpleName(), updatedCategory);
        return updatedCategory;
    }

    public ResponseEntity<CategoryResponse> deleteById(Long id) {

        Category deleted = categoryRepository.findById(id).orElseThrow(() -> {
            throw new ResourceAccessException("");//todo
        });
        if (!categoryRepository.findById(id).get().isBuiltIn()) {
            CategoryResponse categoryResponse = categoryMapper.toResponse(deleted);
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(categoryResponse, HttpStatus.ACCEPTED);//todo postman siliyor ama cevap yok

        }
        return new ResponseEntity<>(categoryMapper.toResponse(deleted), HttpStatus.NOT_FOUND);
    }


    //todo dtolar yapilsin
    public ResponseEntity<Set<CategoryPropertyKey>> getCategoryProporties(Long categoryId) {
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

    // Ilgili Id, Category tablosunda var mi kontrolü
    public Category getCategory(Language language, Long categoryId) {

        log.debug("[{}][getCategory] -> request categoryId: {}", this.getClass().getSimpleName(), categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(language, FriendlyMessageCodes.CATEGORY_NOT_FOUND_EXCEPTION, "Category not found for category id: " + categoryId));

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), category);
        return category;
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