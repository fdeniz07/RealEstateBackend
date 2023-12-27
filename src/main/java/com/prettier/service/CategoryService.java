package com.prettier.service;

import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.CategoryPropertyKey;
import com.prettier.payload.mapper.CategoryMapper;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.repository.CategoryPropertyKeyRepository;
import com.prettier.repository.CategoryPropertyValueRepository;
import com.prettier.repository.CategoryRepository;
import com.prettier.shared.exception.ResourceNotFoundException;
import com.prettier.shared.utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AdvertService advertService;
    private final CategoryPropertyKeyService categoryPropertyKeyService;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;
    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

    public  Page<CategoryResponse> getIsActiveWithPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return categoryRepository.findIsActive(pageable).map(categoryMapper::toResponse);


    }

    public  Page<CategoryResponse> getAllWithPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return categoryRepository.findAll(pageable).map(categoryMapper::toResponse);


    }


    public CategoryResponse getById(Long id) {
        Category category =  categoryRepository.findById(id).orElseThrow(()->{
            throw new ResourceAccessException("");//todo
        });
        return categoryMapper.toResponse(category);
    }

    public ResponseEntity add(CategoryRequest categoryRequest) {

       Category category = categoryMapper.toCategory(categoryRequest);
       Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(HttpStatus.CREATED);


    }

    public ResponseEntity updateById(Long id, CategoryRequest categoryRequest) {

        //!!! Id üzerinden category nesnesi getiriliyor
        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
        }

        //Duplicate kontrolü yapilacak!!!


//              if(!categoryRepository.existsById(id)){
//              throw new ResourceAccessException("B");//todo  ex olustur
//        }
              //todo built in olna bakailmayacak

        Category updatedCategory = categoryMapper.toUpdatedCategory(categoryRequest,id);
        Category savedCategory=  categoryRepository.save(updatedCategory);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);





//        //!!! Id üzerinden teacher nesnesi getiriliyor
//        Optional<Teacher> teacher = teacherRepository.findById(userId);
//
//        //Gelen listenin ici bos mu dolu mu kontrolü (OrElseThrow yapanlarin bunu kullanmasina gerek yok)
//        if (!teacher.isPresent()) {
//            throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_USER_MESSAGE);
//        } else if (lessons.size() == 0) {
//            throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_NOT_FOUND_MESSAGE);
//        } else if (!CheckParameterUpdateMethod.checkParameter(teacher.get(), newTeacher)) { //TODO email kontrol
//            checkUniqueFields.checkDuplicate(newTeacher.getUsername(),
//                    newTeacher.getSsn(),
//                    newTeacher.getPhoneNumber(),
//                    newTeacher.getEmail());
//        }
//        UserRole userRole = userRoleService.getUserRole(RoleType.TEACHER);
//        Teacher updatedTeacher = teacherMapper.createUpdatedTeacher(newTeacher, userId, userRole);
//
//        //!!! Password encode ediliyor
//        updatedTeacher.setPassword(passwordEncoder.encode(newTeacher.getPassword()));
//
//        //!!! LessonProgram set ediliyor
//        updatedTeacher.setLessonsProgramList(lessons); //TODO buraya bakilacak
//        Teacher savedTeacher = teacherRepository.save(updatedTeacher);
//
//        //!!! AdvisorTeacher eklenince yazildi
//        advisorTeacherService.updateAdvisorTeacher(newTeacher.isAdvisorTeacher(), savedTeacher);
//
//        return ResponseMessage.<TeacherResponse>builder()
//                .object(teacherMapper.createTeacherResponse(savedTeacher))
//                .message(SuccessMessages.TEACHER_UPDATED)
//                .httpStatus(HttpStatus.OK)
//                .build();







    }

    public ResponseEntity<CategoryResponse> deleteById(Long id) {

        Category deleted =  categoryRepository.findById(id).orElseThrow(()->{
            throw new ResourceAccessException("");//todo
        });
        if (!categoryRepository.findById(id).get().isBuiltIn()){
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(categoryMapper.toResponse(deleted),HttpStatus.CREATED);

        }
        return new ResponseEntity<>(categoryMapper.toResponse(deleted),HttpStatus.NOT_FOUND);
    }




    //todo dtolar yapilsin
    public ResponseEntity<Set<CategoryPropertyKey>> getCategoryProporties(Long categoryId) {
        Category category =  categoryRepository.findById(categoryId).orElseThrow(()->{
            throw new ResourceAccessException("");//todo
        });

        Set<CategoryPropertyKey> categoryProperties = category.getCategoryPropertyKeys();
        return ResponseEntity.ok(categoryProperties);

    }



    //todo categoryproportieskeyrepo kullanilacak
    public ResponseEntity<CategoryPropertyKey> createCategoryProperty(Long categoryId, CategoryPropertyKey categoryPropertyKey) {
        Category category =  categoryRepository.findById(categoryId).orElseThrow(()->{
            throw new ResourceAccessException("");//todo
        });
        categoryPropertyKey.setCategory(category);
        CategoryPropertyKey createdCategoryProperty = categoryPropertyKeyRepository.save(categoryPropertyKey);
        return ResponseEntity.ok(createdCategoryProperty);
    }

    public ResponseEntity<CategoryPropertyKey> updateCategoryProperty(Long propertyKeyId, CategoryPropertyKey updatedProperty) {

        CategoryPropertyKey existingProperty = categoryPropertyKeyRepository.findById(propertyKeyId).orElseThrow(()->{
            throw new ResourceAccessException("");//todo
        });

        if (existingProperty.isBuiltIn()) {
            return new ResponseEntity<>(existingProperty,HttpStatus.NOT_FOUND);
        }
        existingProperty.setName(updatedProperty.getName());
        existingProperty.setBuiltIn(updatedProperty.isBuiltIn());
        // todo category ve proporty value setlenecek

        CategoryPropertyKey updatedPropertyKey = categoryPropertyKeyRepository.save(existingProperty);
        return ResponseEntity.ok(updatedPropertyKey);

    }

    public ResponseEntity<CategoryPropertyKey> deleteCategoryProperty(Long propertyId) {
        CategoryPropertyKey existingProperty = categoryPropertyKeyRepository.findById(propertyId).orElseThrow(()->{
            throw new ResourceAccessException("");//todo
        });

        if (existingProperty.isBuiltIn()) {
            return new ResponseEntity<>(existingProperty,HttpStatus.NOT_FOUND);
        }
        //todo Delete related category_property_values
        //categoryPropertyValueRepository.deleteRelated(propertyId);

        categoryPropertyKeyRepository.delete(existingProperty);
        return ResponseEntity.ok(existingProperty);

    }










   private Category updatedCategory(Long id, CategoryRequest categoryRequest) {
        return Category.builder()
                .id(id)
                .advertSet(categoryRequest.getAdvertSet())
                .seq(categoryRequest.getSeq())
                .icon(categoryRequest.getIcon())
                .slug(categoryRequest.getSlug())
                .title(categoryRequest.getTitle())
                .builtIn(categoryRequest.isBuiltIn())
                .createAt(categoryRequest.getCreateAt())
                .isActive(categoryRequest.isActive())
                .categoryPropertyKeys(categoryRequest.getCategoryPropertyKeys())
                .updateAt(categoryRequest.getUpdateAt())
                //todo
                .build();
    }




    //class
}
