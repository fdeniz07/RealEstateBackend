package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Favorite;
import com.prettier.payloads.request.concretes.FavoriteRequest;
import com.prettier.payloads.response.concretes.FavoriteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    Favorite toFavorite(FavoriteRequest favoriteRequest);

    FavoriteResponse toResponse(Favorite favorite);

    List<FavoriteResponse> toResponseList(List<Favorite> favorites);

//    @Autowired
//    private ModelMapper modelMapper;
//
//    public Favorite toFavorite(FavoriteRequest favoriteRequest) {
//
//        return modelMapper.map(favoriteRequest, Favorite.class);
//    }
//
//    public FavoriteResponse toResponse(Favorite favorite) {
//
//        return modelMapper.map(favorite, FavoriteResponse.class);
//    }
//    public List<FavoriteResponse> toResponseList(List<Favorite> favorites) {
//
//        return (List<FavoriteResponse>) modelMapper.map(favorites, FavoriteResponse.class);
//    }
}
