package com.travelbnb.controller;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.ReviewsDto;
import com.travelbnb.service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")

public class ReviewsController {
    private ReviewsService reviewsService;

    public ReviewsController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    @RequestMapping("/addReviews")
    public ResponseEntity<ReviewsDto> addReviews(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam Long propertyId,
           @RequestBody ReviewsDto reviewsDto
            ) {

        return new ResponseEntity<>(reviewsService.addReviews(appUser, propertyId, reviewsDto), HttpStatus.CREATED);

    }
}
