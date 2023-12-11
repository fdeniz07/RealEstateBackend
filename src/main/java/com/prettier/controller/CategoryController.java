package com.prettier.controller;


import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('ADMIN','MANAGER','CUSTOMER')")
    @GetMapping()
    public Page<CategoryResponse> getIsActiveWithPage( // TODO getALLWithPAge
                                          @RequestParam(value = "page",defaultValue = "0") int page,
                                          @RequestParam(value = "size",defaultValue = "10") int size,
                                          @RequestParam(value = "sort",defaultValue = "icon") String sort,
                                          @RequestParam(value = "type",defaultValue = "desc") String type
    ) {

        return categoryService.getIsActiveWithPage(page,size,sort,type);
    }


    @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
    @GetMapping("/admin")
    public Page<CategoryResponse> getAllWithPage(
                                          @RequestParam(value = "page",defaultValue = "0") int page,
                                          @RequestParam(value = "size",defaultValue = "10") int size,
                                          @RequestParam(value = "sort",defaultValue = "icon") String sort,
                                          @RequestParam(value = "type",defaultValue = "desc") String type
    ) {

        return categoryService.getAllWithPage(page,size,sort,type);
    }

    @PreAuthorize("hasAuthority('ADMIN','MANAGER','CUSTOMER')")
    @GetMapping("/{id}}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN','MANAGER')")
    @PostMapping()
    public ResponseEntity add(@RequestBody @Valid CategoryRequest categoryRequest) {
        return categoryService.add(categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable Long id,
                                     @RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.updateById(id, categoryRequest);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteById(@PathVariable Long id){
        return categoryService.deleteById(id);

    }
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/{id}/properties")
    public ResponseEntity<Set<CategoryPropertyKey>> getCategoryProperties(@PathVariable("id") Long categoryId) {
        return categoryService.getCategoryProporties(categoryId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping("/{id}/properties")
    public ResponseEntity<CategoryPropertyKey> createCategoryProperty(@PathVariable("id") Long categoryId, @RequestBody CategoryPropertyKey categoryPropertyKey) {
       return categoryService.createCategoryProperty(categoryId,categoryPropertyKey);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/properties/{id}")
    public ResponseEntity<CategoryPropertyKey> updateCategoryProperty(@PathVariable("id") Long propertyKeyId, @RequestBody CategoryPropertyKey updatedProperty) {
        return categoryService.updateCategoryProperty(propertyKeyId,updatedProperty);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/proporties/{id}")
    public ResponseEntity<CategoryPropertyKey> deleteCategoryProperty(@PathVariable("id") Long propertyId) {
       return categoryService.deleteCategoryProperty(propertyId);
    }




  /*
    @GetMapping("/{id}/properties")
    public ResponseEntity getProportiesById(@PathVariable Long categoryId){
        return categoryService.getProportiesById(categoryId);

    }


    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PostMapping("/{id}/properties")
    public ResponseEntity saveProportiesById(@PathVariable Long categoryId){//requeatbody valid request
        return categoryService.saveProportiesById(categoryId);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @PutMapping("/properties/{id}")
    public ResponseEntity saveProportiesById(@PathVariable Long Id){//requeatbody valid request
        return categoryService.ProportiesById(categoryId);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @DeleteMapping("/properties/{id}")
    public ResponseEntity saveProportiesById(@PathVariable Long Id){//requeatbody valid request
        return categoryService.ProportiesById(categoryId);

    }
*/



//class
}
