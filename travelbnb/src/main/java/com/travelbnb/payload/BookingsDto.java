package com.travelbnb.payload;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import lombok.Data;

@Data
public class BookingsDto {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Integer price;
    private Integer totalNights;
    private AppUser appUser;
    private Property property;
}
