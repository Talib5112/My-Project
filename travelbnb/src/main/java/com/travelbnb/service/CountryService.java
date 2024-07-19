package com.travelbnb.service;

import com.travelbnb.payload.CountryDto;

import java.util.List;

public interface CountryService {

    //create country
    CountryDto cretaeCountry(CountryDto countryDto);

    //get country by id
    CountryDto getCountryById(Long id);
    //update country by id
    CountryDto updateCountryById(Long id, CountryDto countryDto);
    //delete country by id
    void deleteById(Long id);
    //get All country by
    List<CountryDto> getAllCountry();
}
