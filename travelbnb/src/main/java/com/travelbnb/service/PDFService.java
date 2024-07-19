package com.travelbnb.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.travelbnb.payload.BookingsDto;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class PDFService {

    private static final String PDF_DIRECTORY ="D://kaif//";
    public boolean generatePDF(String fileName, BookingsDto bookingDto){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk bookingConfirmation = new Chunk("Booking Confirmation", font);
            Chunk guestName = new Chunk("Guest Name: "+ bookingDto.getName(), font);
            Chunk totalNights = new Chunk("Total Nights: "+ bookingDto.getTotalNights(), font);
            Chunk totalPrice = new Chunk("Total Price: "+ bookingDto.getPrice(), font);

            document.add(bookingConfirmation);
            document.add(new Paragraph("\n"));
            document.add(guestName);
            document.add(new Paragraph("\n"));
            document.add(totalNights);
            document.add(new Paragraph("\n"));
            document.add(totalPrice);

            document.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
