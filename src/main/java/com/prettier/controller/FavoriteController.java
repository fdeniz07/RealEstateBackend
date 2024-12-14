package com.prettier.controller;

import com.prettier.payloads.response.concretes.FavoriteResponse;
import com.prettier.service.concretes.FavoriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Favorite", description = "Prettier Homes - Real Estate APIs") //Swagger dökümani icin
@RequestMapping(value = "api/v1.0/favorites")
@Slf4j //Log eklemek icin kullaniyoruz
public class FavoriteController {

    private final FavoriteService favoriteService;


//    @GetMapping("/auth")
//    public ResponseEntity<List<FavoriteResponse>> getAuthenticatedUserFavorites(Authentication authentication) {
//        return favoriteService.getAuthenticatedUserFavorites(authentication);
//    }


    @GetMapping("/admin/{id}")
  public ResponseEntity<List<FavoriteResponse>> getUserFavorites(@PathVariable("id") Long userId) {
       return favoriteService.getUserFavorites(userId);
    }

//    @PostMapping("/{id}/auth")
//    public ResponseEntity<String> addOrRemoveFavorite(@PathVariable("id") Long advertId, Authentication authentication) {
//        return favoriteService.addOrRemoveFavorite(advertId,authentication);
//    }
//    @DeleteMapping
//    public ResponseEntity<String> deleteAllFavorites(Authentication authentication) {
//       return favoriteService.deleteAllFavorites(authentication);
//    }

    @DeleteMapping("ad")//todo duzelt
    public ResponseEntity<String> deleteAllFavorites() {

        return favoriteService.deleteAllFavorites();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavoriteById(@PathVariable("id") Long favoriteId) {

       return favoriteService.deleteFavoriteById(favoriteId);
    }
}
