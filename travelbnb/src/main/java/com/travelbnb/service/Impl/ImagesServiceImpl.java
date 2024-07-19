package com.travelbnb.service.Impl;

import com.travelbnb.entity.Images;
import com.travelbnb.entity.Property;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.repository.ImagesRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.BucketService;
import com.travelbnb.service.ImagesService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImagesServiceImpl implements ImagesService {
    private ImagesRepository imagesRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;

    public ImagesServiceImpl(ImagesRepository imagesRepository, PropertyRepository propertyRepository, BucketService bucketService) {
        this.imagesRepository = imagesRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
    }

    @Override
    public Images uploadImage(MultipartFile file, long propertyId, String bucketName) {

        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () ->new ResourceNotFoundException("Property not found with id : " + propertyId)
        );
        String imageUrl = bucketService.uploadFile(file, bucketName);
        Images images = new Images();
        images.setProperty(property);
        images.setImageUrl(imageUrl);
        Images saved = imagesRepository.save(images);
        return (saved);
    }

//    ImagesDto mapToDto(Images images){
//        ImagesDto imagesDto = new ImagesDto();
//        imagesDto.setId(images.getId());
//        imagesDto.setImageUrl(images.getImageUrl());
//        imagesDto.setPropertyId(images.getPropertyId());
//        return imagesDto;
//    }
//    Images mapToEntity(ImagesDto imagesDto){
//        Images images = new Images();
//        images.setId(imagesDto.getId());
//        images.setImageUrl(imagesDto.getImageUrl());
//        images.setPropertyId(imagesDto.getPropertyId());
//        return images;
//    }
}
