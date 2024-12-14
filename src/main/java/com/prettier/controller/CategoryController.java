package com.prettier.controller;


import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payloads.mapper.CategoryMapper;
import com.prettier.payloads.request.concretes.CategoryRequest;
import com.prettier.payloads.request.concretes.CategoryUpdateRequest;
import com.prettier.shared.exception.globalExceptionHandling.FriendlyMessage;
import com.prettier.shared.exception.globalExceptionHandling.InternalApiResponse;
import com.prettier.payloads.response.concretes.CategoryPropertyKeyResponse;
import com.prettier.payloads.response.concretes.CategoryResponse;
import com.prettier.service.concretes.CategoryManager;
import com.prettier.shared.exception.enums.FriendlyMessageCodes;
import com.prettier.shared.utils.FriendlyMessageUtils;
import com.prettier.shared.utils.enums.Language;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/categories")
public class CategoryController {

    private final CategoryManager categoryService;
    private final CategoryMapper categoryMapper;


    //Not: getAllWithActives() *********************************************************************************************************************************

    // @PreAuthorize("hasAuthority('ADMIN','MANAGER','CUSTOMER')")
    @GetMapping(value = "/{language}/categories") // http://localhost:8080/categories/EN/
    public InternalApiResponse<Page<CategoryResponse>> getCategoriesByActive(@PathVariable("language") Language language,
                                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                                             @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                             @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getCategories]", this.getClass().getSimpleName());
        Page<CategoryResponse> categoryResponses = categoryService.getCategoriesByActive(language, page, size, sort, type);

        log.debug("[{}][getCategories] -> response: {}", this.getClass().getSimpleName(), categoryResponses);
        return InternalApiResponse.<Page<CategoryResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryResponses)
                .build();
    }


    //Not: getAll() *********************************************************************************************************************************

    //  @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
    @GetMapping(value = "/{language}/categories/admin") // http://localhost:8080/categories/EN/admin/
    public InternalApiResponse<Page<CategoryResponse>> getAllWithPage(
            @PathVariable("language") Language language,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        log.debug("[{}][getCategories]", this.getClass().getSimpleName());
        Page<CategoryResponse> categoryResponses = categoryService.getCategories(language, page, size, sort, type);

        log.debug("[{}][getCategories] -> response: {}", this.getClass().getSimpleName(), categoryResponses);
        return InternalApiResponse.<Page<CategoryResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryResponses)
                .build();
    }


    //Not: getById() *********************************************************************************************************************************

    // @PreAuthorize("hasAuthority('ADMIN','MANAGER','CUSTOMER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{categoryId}")
    public InternalApiResponse<CategoryResponse> getCategory(@PathVariable("language") Language language,
                                                             @PathVariable("categoryId") Long id) {
        log.debug("[{}][getCategory] -> request categoryId: {}", this.getClass().getSimpleName(), id);
        CategoryResponse categoryResponse = categoryService.getByCategoryId(language, id);

        log.debug("[{}][getCategory] -> response: {}", this.getClass().getSimpleName(), categoryResponse);
        return InternalApiResponse.<CategoryResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryResponse)
                .build();
    }


    //Not: add() ****************************************************************************************************************************************

    // @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/add")
    public InternalApiResponse<CategoryResponse> addCategory(@PathVariable("language") Language language,
                                                             @RequestBody @Valid CategoryRequest categoryRequest) {
        log.debug("[{}][createCategory] -> request: {}", this.getClass().getSimpleName(), categoryRequest);
        CategoryResponse categoryResponse = categoryService.add(language, categoryRequest);

        log.debug("[{}][createCategory] -> response: {}", this.getClass().getSimpleName(), categoryResponse);

        return InternalApiResponse.<CategoryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.CATEGORY_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(categoryResponse)
                .build();
    }


    //Not: update() *********************************************************************************************************************************

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{categoryId}")
    public InternalApiResponse<CategoryResponse> updateCategory(@PathVariable("language") Language language,
                                                                @PathVariable("categoryId") Long id,
                                                                @RequestBody @Valid  CategoryUpdateRequest categoryUpdateRequest) {

        log.debug("[{}][updateCategory] -> request: {} {}", this.getClass().getSimpleName(), id, categoryUpdateRequest);
        CategoryResponse categoryResponse = categoryService.update(language, categoryUpdateRequest, id);

        log.debug("[{}][updateCategory] -> response: {}", this.getClass().getSimpleName(), categoryResponse);

        return InternalApiResponse.<CategoryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.CATEGORY_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryResponse)
                .build();
    }


    //Not: delete() *********************************************************************************************************************************

    //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{categoryId}")
    public InternalApiResponse<CategoryResponse> deleteDistrict(@PathVariable("language") Language language,
                                                                @PathVariable("categoryId") Long id) {
        log.debug("[{}][deleteCategory] -> request categoryId: {}", this.getClass().getSimpleName(), id);
        CategoryResponse categoryResponse = categoryService.softDelete(language, id);

        log.debug("[{}][deleteCategory] -> response: {}", this.getClass().getSimpleName(), categoryResponse);
        return InternalApiResponse.<CategoryResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.CATEGORY_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryResponse)
                .build();
    }


    //Not: getProperties() ************************************************************************************************************************

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping(value = "/{language}/properties/{id}")
    public InternalApiResponse<Set<CategoryPropertyKeyResponse>> getCategoryProperties(@PathVariable("language") Language language,
                                                                                       @PathVariable("id") Long categoryId) {

        log.debug("[{}][getProperties] -> request categoryId: {}", this.getClass().getSimpleName(), categoryId);
        Set<CategoryPropertyKeyResponse> categoryPropertyKeyResponse = categoryService.getProperties(language, categoryId);

        log.debug("[{}][getProperties] -> response: {}", this.getClass().getSimpleName(), categoryPropertyKeyResponse);
        return InternalApiResponse.<Set<CategoryPropertyKeyResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryPropertyKeyResponse)
                .build();
    }


    //Not: addProperty() ****************************************************************************************************************************

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping("/{language}/properties/{id}")
    public InternalApiResponse<Set<CategoryPropertyKeyResponse>> createCategoryPropertyKey(@PathVariable("language") Language language,
                                                                                           @PathVariable("id") Long categoryId,
                                                                                           @RequestBody @Valid  CategoryPropertyKey categoryPropertyKey) {

        log.debug("[{}][getProperties] -> request categoryId: {}", this.getClass().getSimpleName(), categoryId);
        Set<CategoryPropertyKeyResponse> categoryPropertyKeyResponse = categoryService.getProperties(language, categoryId);

        log.debug("[{}][getProperties] -> response: {}", this.getClass().getSimpleName(), categoryPropertyKeyResponse);
        return InternalApiResponse.<Set<CategoryPropertyKeyResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(categoryPropertyKeyResponse)
                .build();
    }


    //Not: ****() *********************************************************************************************************************************

    //  @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/{language}/properties/{id}")
    public ResponseEntity<CategoryPropertyKey> updateCategoryProperty(@PathVariable("id") Long propertyKeyId, @RequestBody CategoryPropertyKey updatedProperty) {
        return categoryService.updateCategoryProperty(propertyKeyId, updatedProperty);
    }


    //Not: ****() *********************************************************************************************************************************

    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/{language}/properties/{id}")
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
