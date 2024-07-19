package com.travelbnb.payload;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import lombok.Data;

@Data
public class FavouriteDto {
    private Long id;
    private Boolean status;
    private AppUser appUser;
    private Property property;


}
