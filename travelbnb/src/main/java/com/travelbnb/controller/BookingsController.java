package com.travelbnb.controller;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.BookingsDto;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.repository.BookingsRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;
   private BookingsRepository bookingsRepository;
    public BookingsController(BookingsService bookingsService, BookingsRepository bookingsRepository) {
        this.bookingsService = bookingsService;
        this.bookingsRepository = bookingsRepository;
    }
    @RequestMapping
    public ResponseEntity<?> addBookings(
            @AuthenticationPrincipal AppUser appUser,
            @RequestParam long propertyId,
            @RequestBody BookingsDto bookingsDto

            ){
        if(bookingsRepository.existsByName(bookingsDto.getName())){
            return new ResponseEntity<>("exists username",HttpStatus.BAD_REQUEST);
        }
        if(bookingsRepository.existsByEmail(bookingsDto.getEmail())){
            return new ResponseEntity<>("exists email",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bookingsService.addBookings(appUser, propertyId, bookingsDto), HttpStatus.CREATED);

    }
    //for converting filePath into MultiPartFile
    public static MultipartFile convert(String filePath) throws IOException {
        // Load the file from the specified path
        File file = new File(filePath);

        // Read the file content into array
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Convert byte array to a ByteArrayResource
        ByteArrayResource resource = new ByteArrayResource(fileContent);

        // Create MultipartFile from Resource
        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                // You can determine and set the content type here
                try {
                    return Files.probeContentType(file.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public boolean isEmpty() {
                return file.length() == 0;
            }

            @Override
            public long getSize() {
                return file.length();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(fileContent);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                try (InputStream inputStream = new ByteArrayInputStream(fileContent);
                     OutputStream outputStream = new FileOutputStream(dest)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        };

        return multipartFile;
    }
}
