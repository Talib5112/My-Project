package com.travelbnb.controller;

import com.travelbnb.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/s3")
public class ImagesDeleteS3Controller {



        private final S3Service s3Service;

        public ImagesDeleteS3Controller(S3Service s3Service) {
            this.s3Service = s3Service;
        }

        @DeleteMapping("delete/{fileName}")
        public ResponseEntity<String> deleteFile(@PathVariable String keyName) {
            s3Service.deleteFile(keyName);
            return new  ResponseEntity<>("File deleted successfully", HttpStatus.OK);
        }
    }
