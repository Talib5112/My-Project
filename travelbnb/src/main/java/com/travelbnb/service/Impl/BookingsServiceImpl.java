package com.travelbnb.service.Impl;

import com.travelbnb.controller.BookingsController;
import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Bookings;
import com.travelbnb.entity.Property;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.BookingsDto;
import com.travelbnb.repository.BookingsRepository;
import com.travelbnb.repository.PropertyRepository;
import com.travelbnb.service.BookingsService;
import com.travelbnb.service.BucketService;
import com.travelbnb.service.PDFService;
import com.travelbnb.service.SmsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class BookingsServiceImpl implements BookingsService {
    private BookingsRepository bookingsRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    private PDFService pdfService;
    private SmsService smsService;


    public BookingsServiceImpl(BookingsRepository bookingsRepository, PropertyRepository propertyRepository, BucketService bucketService, PDFService pdfService, SmsService smsService) {
        this.bookingsRepository = bookingsRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.pdfService = pdfService;
        this.smsService = smsService;
    }

    @Override
    public BookingsDto addBookings(AppUser appUser, long propertyId, BookingsDto bookingsDto) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                () -> new ResourceNotFoundException("Property not found with this id " + propertyId)
        );
        bookingsDto.setProperty(property);
        bookingsDto.setAppUser(appUser);
        int nightPrice = property.getPrice();
        int totalPrice = nightPrice * bookingsDto.getTotalNights();
        int gstAmount = (totalPrice * 18)/100;
        int finalPrice = totalPrice + gstAmount;
        bookingsDto.setPrice(finalPrice);
        Bookings booking = mapToEntity(bookingsDto);
        Bookings savedBooking = bookingsRepository.save(booking);
        //here i start pdf generating concepts

        boolean b = pdfService.generatePDF("D://kaif//" + bookingsDto.getName()+"-booking-confirmation-id" + savedBooking.getId() + ".pdf", bookingsDto);
        if (b){
            try {
                MultipartFile file = BookingsController.convert("D://kaif//" +bookingsDto.getName()+ "-booking-confirmation-id" + savedBooking.getId() + ".pdf");
                //
                String uploadedFileUrl = bucketService.uploadFile(file, "raj3d");
                System.out.println(uploadedFileUrl);
                //for what's app massage
                String whatsappId = smsService.sendWhatsAppMessage(bookingsDto.getMobile(), "Your booking is confirmed..Click for details: " + uploadedFileUrl);
               System.out.println(whatsappId);

                //for sms
                String smsId = smsService.sendSms(bookingsDto.getMobile(), "Your booking is confirmed. Click for details: " + uploadedFileUrl);
                System.out.println(smsId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

       // BookingsDto dto = mapToDto(savedBooking);
        return mapToDto(savedBooking);

    }
    BookingsDto mapToDto(Bookings bookings){
        BookingsDto bookingsDto = new BookingsDto();
        bookingsDto.setId(bookings.getId());
        bookingsDto.setName(bookings.getName());
        bookingsDto.setEmail(bookings.getEmail());
        bookingsDto.setMobile(bookings.getMobile());
        bookingsDto.setPrice(bookings.getPrice());
        bookingsDto.setTotalNights(bookings.getTotalNights());
        bookingsDto.setProperty(bookings.getProperty());
        bookingsDto.setAppUser(bookings.getAppUser());
        return bookingsDto;
    }
    Bookings mapToEntity(BookingsDto bookingsDto){
        Bookings bookings = new Bookings();
        bookings.setId(bookingsDto.getId());
        bookings.setName(bookingsDto.getName());
        bookings.setEmail(bookingsDto.getEmail());
        bookings.setMobile(bookingsDto.getMobile());
        bookings.setPrice(bookingsDto.getPrice());
        bookings.setTotalNights(bookingsDto.getTotalNights());
        bookings.setProperty(bookingsDto.getProperty());
        bookings.setAppUser(bookingsDto.getAppUser());
        return bookings;
    }
}
