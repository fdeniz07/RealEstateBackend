package com.prettier.controller;

import com.prettier.payload.response.concretes.CountryResponse;
import com.prettier.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("getAll")
    public Page<CountryResponse> getAllWithPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "25") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type
    ) {

        return countryService.getAllWithPage(page,size,sort,type);
    }
}
