package com.travelbnb.service.Impl;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Property;
import com.travelbnb.entity.Reviews;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.ReviewsDto;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.repository.ReviewsRepository;
import com.travelbnb.service.ReviewsService;
import org.springframework.stereotype.Service;


@Service
public class ReviewsServiceImpl implements ReviewsService {

    private ReviewsRepository reviewsRepository;
    private PropertyRepository propertiesRepository;


    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, PropertyRepository propertiesRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertiesRepository = propertiesRepository;
    }

    @Override
    public ReviewsDto addReviews(AppUser appUser, Long propertyId, ReviewsDto reviewDto) {
        Reviews reviews =mapToEntity(reviewDto);
        Property property = propertiesRepository.findById(propertyId).orElseThrow(
                () -> new RuntimeException("Property not found with id : " + propertyId)
        );
       if (reviewsRepository.findReviewsByAppUser(appUser,property)!=null){
           throw new ResourceNotFoundException("Already review exist");
       }else {
           reviews.setProperty(property);
           reviews.setAppUser(appUser);
               Reviews saved = reviewsRepository.save(reviews);
               return mapToDto(saved);

       }
    }
    ReviewsDto mapToDto(Reviews reviews){
        ReviewsDto reviewsDto =new  ReviewsDto();
        reviewsDto.setId(reviews.getId());
        reviewsDto.setRatings(reviews.getRatings());
        reviewsDto.setDescription(reviews.getDescription());
        reviewsDto.setProperty(reviews.getProperty());
        reviewsDto.setAppUser(reviews.getAppUser());
        return reviewsDto;
    }

    Reviews mapToEntity(ReviewsDto reviewsDto){
        Reviews reviews = new Reviews();
        reviews.setId(reviewsDto.getId());
        reviews.setRatings(reviewsDto.getRatings());
        reviews.setDescription(reviewsDto.getDescription());
        reviews.setProperty(reviewsDto.getProperty());
        reviews.setAppUser(reviewsDto.getAppUser());
        return reviews;
    }
}
