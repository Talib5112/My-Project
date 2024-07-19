package com.travelbnb.controller;

import com.travelbnb.payload.LocationDto;
import com.travelbnb.repository.LocationRepository;
import com.travelbnb.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")

public class LocationController {
    @Autowired

    private LocationRepository locationRepository;
    private LocationService locationService;
    public LocationController(LocationRepository locationRepository, LocationService locationService) {
        this.locationRepository = locationRepository;

        this.locationService = locationService;
    }


   @PostMapping("/createLocation")
    public ResponseEntity<?> createLocation(
            @RequestBody LocationDto locationDto
    ){
        if(locationRepository.existsByName(locationDto.getName())){
            return new ResponseEntity<>("Location already exists",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(locationService.createLocation(locationDto),HttpStatus.CREATED);
    }

    //get By id
    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable Long id){
        return new ResponseEntity<> (locationService.getLocationById(id),HttpStatus.OK);
    }
    //update location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocationById(
            @PathVariable Long id,@RequestBody LocationDto locationDto){
        return new ResponseEntity<>(locationService.updateLocationById(id,locationDto),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocationById(@PathVariable Long id){
        locationService.deleteById(id);
        return new ResponseEntity<>("deleted Location successfully",HttpStatus.OK);
    }
    //getAll LocationsList
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocation(){
        return new ResponseEntity<>(locationService.getAllLocation(),HttpStatus.OK);
    }
}
