package com.travelbnb.service.Impl;

import com.travelbnb.entity.Location;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.LocationDto;
import com.travelbnb.repository.LocationRepository;
import com.travelbnb.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LocatinServiceImpl implements LocationService {
    private LocationRepository locationRepository;

    public LocatinServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

//create a new location

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = mapToEntity(locationDto);
        Location saved = locationRepository.save(location);
        return mapToDto(saved);
    }

    // get the location by id
    @Override
    public LocationDto getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Location not found for id :" + id));
        return mapToDto(location);
    }

    @Override
    public LocationDto updateLocationById(Long id, LocationDto locationDto) {
        Location location = locationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Location not found"+ id)
        );
        location.setName(locationDto.getName());
        locationRepository.save(location);
        return mapToDto(location);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);

    }

    @Override
    public List<LocationDto> getAllLocation() {
        List<Location> all = locationRepository.findAll();
        return all.stream().map(
                Location -> mapToDto(Location)).toList();
    }

    LocationDto mapToDto(Location location){
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setName(location.getName());
        return locationDto;
    }

    Location mapToEntity(LocationDto locationDto){
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setName(locationDto.getName());
        return location;
    }
}
