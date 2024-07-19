package com.travelbnb.payload;

import com.travelbnb.entity.Property;
import lombok.Data;

@Data
public class ImagesDto {
    private Long id;
    private String imageUrl;
    private Long propertyId;
}
