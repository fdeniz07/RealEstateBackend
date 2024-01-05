package com.prettier.payload.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Category;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.request.concretes.CategoryUpdateRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    Category toUpdatedCategory(CategoryUpdateRequest categoryUpdateRequest, Long id, @MappingTarget Category category);

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