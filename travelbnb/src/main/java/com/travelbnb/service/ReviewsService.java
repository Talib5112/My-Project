package com.travelbnb.service;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.ReviewsDto;

public interface ReviewsService {

    //add review
    ReviewsDto addReviews(AppUser appUser, Long propertyId,ReviewsDto reviewDto);

}
