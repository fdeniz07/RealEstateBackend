package com.prettier.controller;

import com.prettier.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;
}
