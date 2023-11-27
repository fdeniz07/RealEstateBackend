package com.prettier.controller;

import com.prettier.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("country")
public class CountryController {

    private final CountryService countryService;
}
