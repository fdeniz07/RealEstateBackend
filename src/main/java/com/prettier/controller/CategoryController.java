package com.prettier.controller;


import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.mapper.CategoryMapper;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.request.concretes.CategoryUpdateRequest;
import com.prettier.payload.response.FriendlyMessage;
import com.prettier.payload.response.InternalApiResponse;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.service.concretes.CategoryService;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    // @PreAuthorize("hasAuthority('ADMIN','MANAGER','CUSTOMER')")
    @GetMapping()
    public Page<CategoryResponse> getIsActiveWithPage( // TODO getALLWithPAge
                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                                       @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                       @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        return categoryService.getIsActiveWithPage(page, size, sort, type);
    }


    //  @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
    @GetMapping("/admin")
    public Page<CategoryResponse> getAllWithPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {

        return categoryService.getAllWithPage(page, size, sort, type);
    }

    // @PreAuthorize("hasAuthority('ADMIN','MANAGER','CUSTOMER')")
    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    // @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
    @PostMapping()
    public ResponseEntity add(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.add(categoryRequest);
    }

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{categoryId}")
    public InternalApiResponse<CategoryResponse> updateCategory(@PathVariable("language") Language language,
                                                            @PathVariable("categoryId") Long id,
                                                            @RequestBody CategoryUpdateRequest categoryUpdateRequest) {

        log.debug("[{}][updateCategory] -> request: {} {}", this.getClass().getSimpleName(), id, categoryUpdateRequest);
        Category category = categoryService.update(language,categoryUpdateRequest,id);

        CategoryResponse categoryResponse = categoryMapper.toResponse(category);
        log.debug("[{}][updateCategory] -> response: {}", this.getClass().getSimpleName(), categoryResponse);

        return InternalApiResponse.<CategoryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.ADVERT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryResponse)
                .build();
    }

    //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteById(@PathVariable Long id) {
        return categoryService.deleteById(id);

    }

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/{id}/properties")
    public ResponseEntity<Set<CategoryPropertyKey>> getCategoryProperties(@PathVariable("id") Long categoryId) {
        return categoryService.getCategoryProporties(categoryId);
    }

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping("/{id}/properties")
    public ResponseEntity<CategoryPropertyKey> createCategoryProperty(@PathVariable("id") Long categoryId, @RequestBody CategoryPropertyKey categoryPropertyKey) {
        return categoryService.createCategoryProperty(categoryId, categoryPropertyKey);
    }

    //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/properties/{id}")
    public ResponseEntity<CategoryPropertyKey> updateCategoryProperty(@PathVariable("id") Long propertyKeyId, @RequestBody CategoryPropertyKey updatedProperty) {
        return categoryService.updateCategoryProperty(propertyKeyId, updatedProperty);
    }

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/proporties/{id}")
    public ResponseEntity<CategoryPropertyKey> deleteCategoryProperty(@PathVariable("id") Long propertyId) {
        return categoryService.deleteCategoryProperty(propertyId);
    }

    /* {
            "title": "ev",
            "icon": "ev.jpg",
            "builtIn": true,
            "seq": 1,
            "slug": "deneme amacli veri girisi yapiliyor.En az 5 kelime olmali ",
            "categorySet": [],
            "categoryPropertyKeys": [],
            "active": true
        }
     */
}
