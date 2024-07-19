package com.travelbnb.service.Impl;

import com.travelbnb.entity.Country;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.CountryDto;
import com.travelbnb.repository.CountryRepository;
import com.travelbnb.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryDto cretaeCountry(CountryDto countryDto) {
        Country country = mapToEntity(countryDto);
        Country savedCountry = countryRepository.save(country);

        return mapToDto(savedCountry);
    }

    @Override
    public CountryDto getCountryById(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Country not found for id : " + id)
        );
        return mapToDto(country);
    }

    @Override
    public CountryDto updateCountryById(Long id, CountryDto countryDto) {
        Country country = countryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Country not found for id : " + id)
        );
        country.setName(countryDto.getName());
        countryRepository.save(country);
        return mapToDto(country);
    }

    @Override
    public void deleteById(Long id) {
        countryRepository.deleteById(id);

    }

    @Override
    public List<CountryDto> getAllCountry() {
        List<Country> all = countryRepository.findAll();
        return all.stream().map(
                country -> mapToDto(country)
        ).toList();

    }

    CountryDto mapToDto(Country country){
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.getId());
        countryDto.setName(country.getName());
        return countryDto;
    }

    Country mapToEntity(CountryDto countryDto) {
        Country country = new Country();
        country.setId(countryDto.getId());
        country.setName(countryDto.getName());
        return country;
    }
}
