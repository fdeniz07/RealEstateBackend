package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Category;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import lombok.*;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Category toCategory(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }
    public Category toUpdatedCategory(CategoryRequest categoryRequest) {
        TypeMap<Category,CategoryRequest> typeMapper=modelMapper.createTypeMap(Category.class,CategoryRequest.class);

        Condition notNull = ctx -> ctx.getSource() != null;
         typeMapper.addMappings(mapper -> mapper. when(notNull).map(Category::getTitle, CategoryRequest::setTitle));
         typeMapper.addMappings(mapper -> mapper. when(notNull).map(Category::getUpdateAt, CategoryRequest::setUpdateAt));
         typeMapper.addMappings(mapper -> mapper. when(notNull).map(Category::getSlug, CategoryRequest::setSlug));
         typeMapper.addMappings(mapper -> mapper. when(notNull).map(Category::getIcon, CategoryRequest::setIcon));
         typeMapper.addMappings(mapper -> mapper. when(notNull).map(Category::getSeq, CategoryRequest::setSeq));
        Category category=new Category();
          return category;//todo buraya bakilacak
    }

    public CategoryResponse toResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }



}
