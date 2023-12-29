package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Category;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import lombok.*;
import org.modelmapper.Condition;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMapper {

    private ModelMapper modelMapper;

    public Category toCategory(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }

    public Category toUpdatedCategory(CategoryRequest categoryRequest, Category existCategory) {

//        return Category.builder()
//                .title(categoryRequest.getTitle())
//                .icon(categoryRequest.getIcon())
//                .builtIn(categoryRequest.isBuiltIn())
//                .seq(categoryRequest.getSeq())
//                .slug(categoryRequest.getSlug())
//                .isActive(categoryRequest.isActive())
//                .test(categoryRequest.getTest())
//                .createAt(categoryRequest.getCreateAt())
//                .updateAt(categoryRequest.getUpdateAt())
//                .test(categoryRequest.getTest())
//                .build();

//        Condition notNull = ctx -> ctx.getSource() != null;
//
//        modelMapper.typeMap(Category.class, CategoryRequest.class).addMappings(mapper -> mapper.when(notNull).map(Category::getTitle, CategoryRequest::setTitle));
//        modelMapper.typeMap(Category.class, CategoryRequest.class).addMappings(mapper -> mapper.when(notNull).map(Category::isActive, CategoryRequest::setActive));
//        modelMapper.typeMap(Category.class, CategoryRequest.class).addMappings(mapper -> mapper.when(notNull).map(Category::getSlug, CategoryRequest::setSlug));
//        modelMapper.typeMap(Category.class, CategoryRequest.class).addMappings(mapper -> mapper.when(notNull).map(Category::getIcon, CategoryRequest::setIcon));
//        modelMapper.typeMap(Category.class, CategoryRequest.class).addMappings(mapper -> mapper.when(notNull).map(Category::getSeq, CategoryRequest::setSeq));
//        modelMapper.typeMap(Category.class, CategoryRequest.class).addMappings(mapper -> mapper.when(notNull).map(Category::isBuiltIn, CategoryRequest::setBuiltIn));
//        modelMapper.typeMap(CategoryRequest.class, Category.class).addMappings(mapper -> mapper.map(CategoryRequest::getCreateAt, Category::setCreateAt));

//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
//        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

//        categoryRequest.setId(id);
        modelMapper.map(categoryRequest, Category.class);
        return existCategory;
    }

    public CategoryResponse toResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }


}
