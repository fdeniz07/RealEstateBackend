package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.Favorite;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.request.concretes.FavoriteRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.payload.response.concretes.FavoriteResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FavoriteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Favorite toFavorite(FavoriteRequest favoriteRequest) {
        return modelMapper.map(favoriteRequest, Favorite.class);
    }

    public FavoriteResponse toResponse(Favorite favorite) {

        return modelMapper.map(favorite, FavoriteResponse.class);
    }
    public List<FavoriteResponse> toResponseList(List<Favorite> favorites) {

        return (List<FavoriteResponse>) modelMapper.map(favorites, FavoriteResponse.class);
    }

}
