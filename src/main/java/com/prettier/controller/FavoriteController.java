package com.prettier.controller;

import com.prettier.payload.response.concretes.FavoriteResponse;
import com.prettier.service.concretes.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("favorites")
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
