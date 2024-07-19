package com.travelbnb.service;

import com.travelbnb.payload.LocationDto;

import java.util.List;

public interface LocationService {

    //create location

    LocationDto createLocation(LocationDto locationDto);

    LocationDto getLocationById(Long id);
    // update location by id
    LocationDto updateLocationById(Long id, LocationDto locationDto);
    //delete
    void deleteById(Long id);

    //get all lacation details
    List<LocationDto> getAllLocation();

}
