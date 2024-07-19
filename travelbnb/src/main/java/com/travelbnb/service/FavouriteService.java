package com.travelbnb.service;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import com.travelbnb.payload.FavouriteDto;

import java.util.List;

public interface FavouriteService {
    //add favourite
    FavouriteDto addFavourite(Long appUserId, Long propertyId, FavouriteDto favouriteDto);

    //raed  Favourite
    FavouriteDto readById(Long Id);
    //update Favourite
    FavouriteDto updateFavourite(Long Id,FavouriteDto favouriteDto);

    //delete Favourite
    void deleteFavourite(Long Id);

    //read all Favourites
   List<FavouriteDto> readAllFavourite();
}
