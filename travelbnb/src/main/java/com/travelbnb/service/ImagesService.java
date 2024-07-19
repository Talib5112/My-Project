package com.travelbnb.service;

import com.travelbnb.entity.Images;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    Images uploadImage(MultipartFile file, long propertyId, String bucketName);

}
