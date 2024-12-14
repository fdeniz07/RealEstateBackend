package com.prettier.service.concretes;

import com.prettier.entity.concretes.Favorite;
import com.prettier.payloads.mapper.FavoriteMapper;
import com.prettier.payloads.response.concretes.FavoriteResponse;
import com.prettier.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;

//    public ResponseEntity<List<FavoriteResponse>> getAuthenticatedUserFavorites(Authentication authentication) {
        // Kullanıcının kimlik doğrulamasını kontrol et
//        if (authentication != null && authentication.isAuthenticated()) {
            // Kullanıcının kimlik bilgilerini al
   //         User authenticatedUser = (User) authentication.getPrincipal();

            // Kullanıcının favori verilerini al
     //       List<Favorite> favorites = favoriteRepository.findByUser(authenticatedUser);

       //    List<FavoriteResponse>  favoriteResponseList =favoriteMapper.toResponseList(favorites);
            // Favori verilerini döndür
       //     return ResponseEntity.ok(favoriteResponseList);
     //   } else {
            // Kimlik doğrulama başarısız olduğunda 401 Unauthorized yanıt döndür
      //      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    //    }

  //  }

    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(Long userId) {
        // Kullanıcının favori verilerini al
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        List<FavoriteResponse>  favoriteResponseList =favoriteMapper.toResponseList(favorites);

        // Favori verilerini döndür
        return ResponseEntity.ok(favoriteResponseList);

    }

//    public ResponseEntity<String> addOrRemoveFavorite(Long advertId, Authentication authentication) {
//        // Kullanıcının kimlik doğrulamasını kontrol et
//        if (authentication != null && authentication.isAuthenticated()) {
//            // Kullanıcının kimlik bilgilerini al
//            User authenticatedUser = (User) authentication.getPrincipal();
//
//            // Favori verilerini kontrol et
//            Optional<Favorite> favoriteOptional = favoriteRepository.findByUserAndAdvertId(authenticatedUser, advertId);
//
//            if (favoriteOptional.isPresent()) {
//                // Favori verisi mevcut ise, favorilerden kaldır
//                favoriteRepository.delete(favoriteOptional.get());
////todo advert silinecek
//                return ResponseEntity.ok("Advert removed from favorites");
//            } else {
//                // Favori verisi mevcut değil ise, favorilere ekle
//                Favorite favorite = new Favorite();
//                favorite.setUser(authenticatedUser);
//                //todo advert eklenecek
//
//                // Favoriye ait diğer verileri burada ayarla (ör. advert)
//                favoriteRepository.save(favorite);
//
//                return ResponseEntity.ok("Advert added to favorites");
//            }
//        } else {
//            // Kimlik doğrulama başarısız olduğunda 401 Unauthorized yanıt döndür
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//
//    }
//
//    public ResponseEntity<String> deleteAllFavorites(Authentication authentication) {
//        // Kullanıcının kimlik doğrulamasını kontrol et
//        if (authentication != null && authentication.isAuthenticated()) {
//            // Kullanıcının kimlik bilgilerini al
//            User authenticatedUser = (User) authentication.getPrincipal();
//
//            // Kullanıcının tüm favori verilerini sil
//            favoriteRepository.deleteByUser(authenticatedUser);
//
//            // Başarılı bir yanıt döndür
//            return ResponseEntity.ok("All favorites deleted successfully");
//        } else {
//            // Kimlik doğrulama başarısız olduğunda 401 Unauthorized yanıt döndür
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    public ResponseEntity<String> deleteAllFavorites() {
        // Tüm favori verilerini sil
        favoriteRepository.deleteAll();

        // Başarılı bir yanıt döndür
        return ResponseEntity.ok("All favorites deleted successfully");
    }

    public ResponseEntity<String> deleteFavoriteById(Long favoriteId) {
        // Check if the favorite exists
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(favoriteId);
        if (favoriteOptional.isPresent()) {
            Favorite favorite = favoriteOptional.get();
            // Delete the favorite
            favoriteRepository.delete(favorite);
            return ResponseEntity.ok("Favorite deleted successfully");
        } else {
            // Return a not found response if the favorite doesn't exist
            return ResponseEntity.notFound().build();
        }
    }
}
