package com.prettier.controller;

import com.prettier.payload.response.concretes.AdvertResponse;
import com.prettier.service.concretes.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("adverts")
public class AdvertController {

    private final AdvertService advertService;

    @GetMapping("/getAll")
    public Page<AdvertResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {

        return advertService.getAll(page, size, sort, type);
    }

    @GetMapping("/getListWithActive")
    public Page<AdvertResponse> getListWithActive(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {

        return advertService.getListWithActive(page, size, sort, type);
    }


}
