package com.prettier.payload.mapper;

import com.prettier.entity.concretes.Category;
import com.prettier.entity.concretes.Favorite;
import com.prettier.payload.request.concretes.CategoryRequest;
import com.prettier.payload.request.concretes.FavoriteRequest;
import com.prettier.payload.response.concretes.CategoryResponse;
import com.prettier.payload.response.concretes.FavoriteResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteMapper {

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
