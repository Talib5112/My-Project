package com.travelbnb.controller;

import com.travelbnb.entity.Images;
import com.travelbnb.repository.ImagesRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.BucketService;
import com.travelbnb.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {
    // Add image upload and retrieval endpoints here
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesService imagesService;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;


    public ImagesController(ImagesRepository imagesRepository, ImagesService imagesService, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imagesRepository = imagesRepository;
        this.imagesService = imagesService;

        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }


    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Images> uploadImage(
            @RequestParam MultipartFile file,
            @PathVariable long propertyId,
            @PathVariable String bucketName

    ){
        return new ResponseEntity<>(imagesService.uploadImage(file, propertyId, bucketName), HttpStatus.CREATED);
    }


    // According to sir
//    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Images>uploadPropertyPhotos(
//            @RequestParam MultipartFile file,
//            @PathVariable long propertyId,
//            @PathVariable String bucketName
//
//    ){
//        Property property = propertyRepository.findById(propertyId).get();
//        String imageUrl = bucketService.uploadFile(file, bucketName);
//        Images image = new Images();
//        image.setProperty(property);
//        image.setImageUrl(imageUrl);
//        Images saved = imagesRepository.save(image);
//        return new ResponseEntity<>(saved, HttpStatus.CREATED);
//    }
}
