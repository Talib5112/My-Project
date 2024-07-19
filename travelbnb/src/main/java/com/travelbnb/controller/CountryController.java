package com.travelbnb.controller;

import com.travelbnb.payload.CountryDto;
import com.travelbnb.repository.CountryRepository;
import com.travelbnb.service.CountryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private CountryRepository countryRepository;
    private CountryService countryService;

    public CountryController(CountryRepository countryRepository, CountryService countryService) {
        this.countryRepository = countryRepository;
        this.countryService = countryService;
    }
    @PostMapping("/createCountry")
    public ResponseEntity<?> createCountry(
            @RequestBody CountryDto countryDto


    ){
        if(countryRepository.existsByName(countryDto.getName())){
            return new ResponseEntity<>("exists name", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(countryService.cretaeCountry(countryDto) , HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(
            @PathVariable  long id
    ){
        return new ResponseEntity<>(countryService.getCountryById(id) ,HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountryById(
            @PathVariable long id,
            @RequestBody CountryDto countryDto
    ){
        return new ResponseEntity<>(countryService.updateCountryById(id, countryDto) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountryById(
            @PathVariable Long id
    ){
        countryService.deleteById(id);
        return new ResponseEntity<>("deleted country successfully", HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries(){
        return new ResponseEntity<>(countryService.getAllCountry(), HttpStatus.OK);
    }
}
