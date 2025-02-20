package com.prettier.service.concretes;

import com.prettier.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * ImageManager.java
 * Service class for managing image-related operations.
 * Implements Serializable for object serialization support.
 */
@Service
@RequiredArgsConstructor
public class ImageManager implements Serializable {

    private final ImageRepository imageRepository;
}
