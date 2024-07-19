package com.travelbnb.service.Impl;


import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Favourite;
import com.travelbnb.entity.Property;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.FavouriteDto;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.repository.FavouriteRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.FavouriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteServiceImpl  implements FavouriteService {
    private FavouriteRepository favouriteRepository;

    private PropertyRepository propertyRepository;
    private AppUserRepository appUserRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository, AppUserRepository appUserRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
    }


    @Override
    public FavouriteDto addFavourite(Long appUserId, Long propertyId, FavouriteDto favouriteDto) {
        Favourite favourite = mapToEntity(favouriteDto);

        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () ->new ResourceNotFoundException("Property id is not found " + propertyId)
        );
        AppUser appUser = appUserRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser id is not found " + appUserId)
        );

        favourite.setProperty(property);
        favourite.setAppUser(appUser);
        Favourite saved = favouriteRepository.save(favourite);
        return mapToDto(saved);
    }

    @Override
    public FavouriteDto readById(Long Id) {
        Favourite favourite = favouriteRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("Favourite id is not found " + Id)
        );
        return mapToDto(favourite);
    }

    @Override
    public FavouriteDto updateFavourite(Long Id, FavouriteDto favouriteDto) {
        Favourite favourite = favouriteRepository.findById(Id).orElseThrow(
                () -> new ResourceNotFoundException("Favourite Id not found"+Id)
        );
        favourite.setStatus(favouriteDto.getStatus());
        return mapToDto(favourite);
    }

    @Override
    public void deleteFavourite(Long Id) {
        favouriteRepository.deleteById(Id);
    }

    @Override
    public List<FavouriteDto> readAllFavourite() {
        List<Favourite> all = favouriteRepository.findAll();
        return all.stream().map(Favourite -> mapToDto(Favourite)).toList();
    }


    //add favourite crud
    FavouriteDto mapToDto(Favourite favourite){
        FavouriteDto favouriteDto = new FavouriteDto();
        favouriteDto.setId(favourite.getId());
        favouriteDto.setStatus(favourite.getStatus());
        favouriteDto.setProperty(favourite.getProperty());
        favouriteDto.setAppUser(favourite.getAppUser());
        return favouriteDto;
    }

    Favourite mapToEntity(FavouriteDto favouriteDto){
        Favourite favourite = new Favourite();
        favourite.setId(favouriteDto.getId());
        favourite.setStatus(favouriteDto.getStatus());
        favourite.setProperty(favouriteDto.getProperty());
        favourite.setAppUser(favouriteDto.getAppUser());
        return favourite;
    }
}




