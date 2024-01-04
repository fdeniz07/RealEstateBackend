package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Category;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    Category toUpdateResponse(CategoryRequest categoryRequest,Category existcategory);

//    @Autowired
//    private ModelMapper modelMapper;
//
//    public Category toCategory(CategoryRequest categoryRequest) {
//        return modelMapper.map(categoryRequest, Category.class);
//    }
//
//
//    public CategoryResponse toResponse(Category category) {
//        return modelMapper.map(category, CategoryResponse.class);
//    }
//
//
//
//
//    public Category toUpdateResponse(CategoryRequest categoryRequest,Category existcategory) {
//        modelMapper.map(categoryRequest, existcategory);
//        return existcategory;
//    }



}