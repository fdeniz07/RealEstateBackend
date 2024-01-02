package com.prettier.controller;

import com.prettier.service.concretes.ImageManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("images")
public class ImageController {

    private final ImageManager imageService;

}
