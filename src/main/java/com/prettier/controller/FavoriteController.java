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

/**
 * REST controller for managing user favorites in the system.
 * Handles endpoints for retrieving, adding, and deleting favorite properties.
 *
 * <p>This controller provides functionality for users to manage their favorite
 * real estate properties.</p>
 *
 * @author Fatih Deniz
 * @version 1.0
 */
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


    /**
     * Retrieves all favorites for a specific user (admin access).
     *
     * @param userId The ID of the user whose favorites to retrieve
     * @return A response entity containing a list of favorite responses
     */
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

    /**
     * Deletes all favorites from the system.
     *
     * @return A response entity containing a result message
     */
    @DeleteMapping("ad")//todo duzelt
    public ResponseEntity<String> deleteAllFavorites() {

        return favoriteService.deleteAllFavorites();
    }

    /**
     * Deletes a specific favorite by its ID.
     *
     * @param favoriteId The ID of the favorite to delete
     * @return A response entity containing a result message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFavoriteById(@PathVariable("id") Long favoriteId) {

       return favoriteService.deleteFavoriteById(favoriteId);
    }
}
