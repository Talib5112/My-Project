package com.travelbnb.controller;


import com.travelbnb.entity.Property;
import com.travelbnb.payload.PropertyUserDto;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.Impl.PropertyServiceImpl;
import com.travelbnb.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyRepository  propertyRepository ;
    @Autowired
    private PropertyService propertyService;
    private PropertyServiceImpl propertyServiceImpl;

    public PropertyController(PropertyRepository propertyRepository, PropertyService propertyService, PropertyServiceImpl propertyServiceImpl) {
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;

        this.propertyServiceImpl = propertyServiceImpl;
    }
    @GetMapping("/searchProperties")
    public ResponseEntity<List<Property>>searchProperty(@RequestParam String name

    ){
        List<Property> properties = propertyRepository.searchProperty(name);
        return new ResponseEntity<>(properties,HttpStatus.OK);
    }

    //crud operation start here
    //Here we are create a property value object
    @PostMapping("/createProperty")
    public ResponseEntity<?> createProperty(
            @RequestParam Long countryId,
            @RequestParam Long locationId,
            @RequestBody PropertyUserDto propertyUserDto
    ){
        if(propertyRepository.existsByName(propertyUserDto.getName())){
            return new ResponseEntity<>("Property name already exists",HttpStatus.BAD_REQUEST);
        }
       return new ResponseEntity<>( propertyService.createProperty(countryId, locationId, propertyUserDto),HttpStatus.CREATED);

    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<PropertyUserDto> getPropertyById(@PathVariable Long id){

            return new ResponseEntity<> (propertyService.getPropertyById(id),HttpStatus.OK);


    }

    //get all user properties


    //update user by id
    @PutMapping("/{id}")
    public ResponseEntity<PropertyUserDto> updatePropertyById(
            @PathVariable Long id,
            @RequestBody PropertyUserDto propertyUserDto){
        return new ResponseEntity<> (propertyService.updatePropertyById(id,propertyUserDto),HttpStatus.OK);
    }

     //delete property user
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteById(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return new ResponseEntity<>("Deleted Record....",HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<PropertyUserDto>> getAllProperty(){
        return new ResponseEntity<> (propertyService.getAllProperty(),HttpStatus.OK);
    }

}
