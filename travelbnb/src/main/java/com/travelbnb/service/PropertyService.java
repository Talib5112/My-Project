package com.travelbnb.service;

import com.travelbnb.payload.PropertyUserDto;

import java.util.List;

public interface PropertyService {

    //create user with mappings for mapping use country id and location id
   PropertyUserDto createProperty(Long countryId, Long locationId, PropertyUserDto propertyuserDto);

   //get Property
    PropertyUserDto getPropertyById(Long id);

    //update Property
    PropertyUserDto updatePropertyById(Long id, PropertyUserDto propertyUserDto);

    //delete Property
    void deleteProperty(Long id);

    // get All Properties users
    List<PropertyUserDto>getAllProperty();
}
