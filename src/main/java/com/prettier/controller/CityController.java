package com.prettier.controller;

import com.prettier.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;
}
