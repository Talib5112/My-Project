package com.travelbnb.service;

import com.travelbnb.entity.AppUser;
import com.travelbnb.payload.BookingsDto;

public interface BookingsService {
    BookingsDto addBookings(AppUser appUser,long propertyId,BookingsDto bookingsDto);
}
