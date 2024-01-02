package com.prettier.controller;

import com.prettier.service.concretes.LogManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("logs")

public class LogController {

    private final LogManager logService;

}
