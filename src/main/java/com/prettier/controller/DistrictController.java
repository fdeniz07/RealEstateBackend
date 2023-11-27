package com.prettier.controller;

import com.prettier.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("district")
public class DistrictController {

    private final DistrictService districtService;
}
