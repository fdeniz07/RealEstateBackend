package com.prettier.controller;


import com.prettier.service.concretes.AdvertTypeManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing advertisement types in the system.
 * Handles endpoints for advertisement type operations.
 *
 * <p>This controller manages the different types of advertisements that can be created
 * in the real estate system.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Advert-Type", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/advert-types")
@Slf4j
public class AdvertTypeController {

    private final AdvertTypeManager advertTypeService;
}
