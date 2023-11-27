package com.prettier.controller;

import com.prettier.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("logs")

public class LogController {

    private final LogService logService;

}
