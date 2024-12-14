package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Category;
import com.prettier.payloads.request.concretes.CategoryRequest;
import com.prettier.payloads.request.concretes.CategoryUpdateRequest;
import com.prettier.payloads.response.concretes.CategoryResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryRequest categoryRequest);

    CategoryResponse toResponse(Category category);

    @Mapping(target = "advertSet", source = "categoryUpdateRequest.adverts")
    @Mapping(target = "categoryPropertyKeys", source = "categoryUpdateRequest.categoryPropertyKeys")
    Category toUpdatedCategory(CategoryUpdateRequest categoryUpdateRequest,@MappingTarget  Category existingCategory);

}