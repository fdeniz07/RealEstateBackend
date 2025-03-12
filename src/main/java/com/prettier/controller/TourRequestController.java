package com.prettier.controller;


import com.prettier.payloads.mapper.TourRequestMapper;
import com.prettier.service.concretes.TourRequestManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing property tour requests in the system.
 * Handles endpoints for creating, retrieving, updating, and managing property tour requests.
 *
 * <p>This controller provides functionality for users to request property tours
 * and for administrators to manage these requests.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Tour-Request", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/tour-requests")
@Slf4j
public class TourRequestController {

    private final TourRequestManager tourRequestService;
    private final TourRequestMapper tourRequestMapper;
}
