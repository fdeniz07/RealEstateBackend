package com.prettier.controller;

import com.prettier.payload.response.concretes.DistrictResponse;
import com.prettier.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("districts")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("getAll")
    public Page<DistrictResponse> getAllWithPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "25") int size,
            @RequestParam(value = "sort",defaultValue = "name") String sort,
            @RequestParam(value = "type",defaultValue = "asc") String type
    ) {

        return districtService.getAllWithPage(page,size,sort,type);
    }
}
