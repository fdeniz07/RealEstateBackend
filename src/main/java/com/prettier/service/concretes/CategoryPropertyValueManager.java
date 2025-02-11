package com.prettier.service.concretes;

import com.prettier.repository.CategoryPropertyValueRepository;
import com.prettier.service.abstracts.CategoryPropertyValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation class that manages Category Property Value operations.
 * This class handles the business logic for category property values and their relationships.
 * Implements the CategoryPropertyValueService interface.
 */
@Service
@RequiredArgsConstructor
public class CategoryPropertyValueManager implements CategoryPropertyValueService {

    private final CategoryPropertyValueRepository categoryPropertyValueRepository;

}
