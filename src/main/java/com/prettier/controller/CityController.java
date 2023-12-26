package com.prettier.controller;

import com.prettier.payload.response.concretes.CityResponse;
import com.prettier.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;

    @GetMapping("getAll") // http://localhost:8080/cities/getAll
    public Page<CityResponse> getAllWithPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "41") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type
    ) {


        return cityService.getAllWithPage(page,size,sort,type);
    }
}
