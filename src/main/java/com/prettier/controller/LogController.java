package com.prettier.controller;

import com.prettier.service.concretes.LogManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing system logs.
 * Handles endpoints for retrieving and managing application logs.
 *
 * <p>This controller provides functionality for administrators to view
 * and manage system logs for audit and troubleshooting purposes.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Log", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/logs")
@Slf4j
public class LogController {

    private final LogManager logService;

}
