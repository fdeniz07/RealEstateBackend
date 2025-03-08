package com.prettier.controller;

import com.prettier.service.concretes.ImageManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing image operations in the system.
 * Handles endpoints for uploading, retrieving, and managing property images.
 *
 * <p>This controller provides functionality for real estate property images
 * including upload, download, and management operations.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Image", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/images")
@Slf4j
public class ImageController {

    private final ImageManager imageService;

}
