package com.travelbnb.controller;

import com.travelbnb.payload.FavouriteDto;
import com.travelbnb.repository.FavouriteRepository;
import com.travelbnb.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favourite")
public class FavouriteController {

    private FavouriteRepository favouriteRepository;
    @Autowired
    private FavouriteService favouriteService;

    public FavouriteController(FavouriteRepository favouriteRepository, FavouriteService favouriteService) {
        this.favouriteRepository = favouriteRepository;
        this.favouriteService = favouriteService;
    }

    // Add CRUD operations for Favourite Dto
      @PostMapping("/add")
    public ResponseEntity<FavouriteDto> addFavourite(
            @RequestParam Long appUserId,
            @RequestParam Long propertyId,
            @RequestBody FavouriteDto favouriteDto
    ) {
        return new ResponseEntity<>(favouriteService.addFavourite(appUserId, propertyId, favouriteDto), HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<FavouriteDto>readById(@PathVariable Long id){
        return new ResponseEntity<>(favouriteService.readById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavouriteDto> updateFavourite(@PathVariable Long id, @RequestBody FavouriteDto favouriteDto){
        return new ResponseEntity<>(favouriteService.updateFavourite(id, favouriteDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteById(@PathVariable Long id){
        favouriteService.deleteFavourite(id);
        return new ResponseEntity<>("Deleted favourite successfully", HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<FavouriteDto>>readAllFavourite(){
        return new ResponseEntity<>(favouriteService.readAllFavourite(), HttpStatus.OK);

    }

    }
