package com.prettier.controller;


import com.prettier.service.TourRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("tour-requests")
public class TourRequestController {

    private final TourRequestService tourRequestService;
}
