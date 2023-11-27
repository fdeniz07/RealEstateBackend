package com.prettier.controller;

import com.prettier.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("adverts")
public class AdvertController {

    private final AdvertService adviceService;
}
