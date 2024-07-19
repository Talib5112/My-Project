package com.travelbnb.payload;

import com.travelbnb.entity.Country;
import com.travelbnb.entity.Location;
import lombok.Data;

@Data
public class PropertyUserDto {

    private Long id;

    private String name;

    private Integer noGuests;

    private Integer noBadrooms;

    private Integer noBathrooms;

    private Integer price;
    private Country country;
    private Location location;
}
