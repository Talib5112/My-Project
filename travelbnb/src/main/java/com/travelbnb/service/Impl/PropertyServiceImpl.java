package com.travelbnb.service.Impl;

import com.travelbnb.entity.Country;
import com.travelbnb.entity.Location;
import com.travelbnb.entity.Property;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.PropertyUserDto;
import com.travelbnb.repository.CountryRepository;
import com.travelbnb.repository.LocationRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.PropertyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl  implements PropertyService {
    private PropertyRepository propertyRepository;

    private CountryRepository countryRepository;

    private LocationRepository locationRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, CountryRepository countryRepository, LocationRepository locationRepository) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public PropertyUserDto createProperty(Long countryId, Long locationId, PropertyUserDto propertyuserDto) {
        Property property =mapToEntity(propertyuserDto);
        //mapping starts here //import countryRepository and locationRepository
        Country country = countryRepository.findById(countryId).orElseThrow(
                () -> new ResourceNotFoundException("Country not found with id:" + countryId)
        );
        property.setCountry(country);
        Location location = locationRepository.findById(locationId).orElseThrow(
                () -> new ResourceNotFoundException("Location not found with id : " + locationId)
        );
        //mapping end here


        property.setLocation(location);
        Property savedProperty = propertyRepository.save(property);
        return mapToDto(savedProperty);

    }
      //get user by id (yaha pe user find ho raha h)
    @Override
    public PropertyUserDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("property not found with id : " + id)
        );
        return mapToDto(property);
    }


     // update property
    @Override
    public PropertyUserDto updatePropertyById(Long id, PropertyUserDto propertyUserDto) {
        Property property = propertyRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Property not found : " + id)
        );
       // property.setId(propertyUserDto.getId());
        property.setName(propertyUserDto.getName());
        property.setNoBadrooms(propertyUserDto.getNoBadrooms());
        property.setNoBathrooms(propertyUserDto.getNoBathrooms());
        property.setNoGuests(propertyUserDto.getNoGuests());
        property.setPrice(propertyUserDto.getPrice());



        Property updatedProperty = propertyRepository.save(property);
        return mapToDto(updatedProperty);

    }



    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);

    }

    @Override
    public List<PropertyUserDto> getAllProperty() {
        List<Property> allProperty = propertyRepository.findAll();

        return allProperty.stream().map(Property -> mapToDto (Property) ).toList();
    }



    PropertyUserDto mapToDto(Property property){
        PropertyUserDto propertyUserDto = new PropertyUserDto();
        propertyUserDto.setId(property.getId());
        propertyUserDto.setName(property.getName());
        propertyUserDto.setNoBadrooms(property.getNoBadrooms());
        propertyUserDto.setNoBathrooms(property.getNoBathrooms());
        propertyUserDto.setNoGuests(property.getNoGuests());
        propertyUserDto.setPrice(property.getPrice());
        propertyUserDto.setCountry(property.getCountry());
        propertyUserDto.setLocation(property.getLocation());
        return propertyUserDto;

    }
    Property mapToEntity(PropertyUserDto propertyuserDto){
        Property property = new Property();
        property.setId(propertyuserDto.getId());
        property.setName(propertyuserDto.getName());
        property.setNoBadrooms(propertyuserDto.getNoBadrooms());
        property.setNoBathrooms(propertyuserDto.getNoBathrooms());
        property.setNoGuests(propertyuserDto.getNoGuests());
        property.setPrice(propertyuserDto.getPrice());
        property.setLocation(propertyuserDto.getLocation());
        property.setCountry(propertyuserDto.getCountry());

        return property;



    }
}
