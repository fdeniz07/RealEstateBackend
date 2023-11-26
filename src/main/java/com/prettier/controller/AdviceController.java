package com.prettier.controller;

import com.prettier.service.AdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("advice")
public class AdviceController {

    private final AdviceService adviceService;
}
