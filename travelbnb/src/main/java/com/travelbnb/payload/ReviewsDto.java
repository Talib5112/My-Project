package com.travelbnb.payload;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import lombok.Data;

@Data
public class ReviewsDto {

    private Long id;
    private Boolean ratings;
    private String description;
    private AppUser appUser;
    private Property property;
}
