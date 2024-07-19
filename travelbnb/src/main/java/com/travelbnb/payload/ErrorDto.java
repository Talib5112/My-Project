package com.travelbnb.payload;

import lombok.Data;

import java.util.Date;

@Data

public class ErrorDto {

    private String message;
    private Date date;
    private String webRequest;

    public ErrorDto(String message,Date date,String webReqeust){
        this.message=message;
        this.date=date;
        this.webRequest=webReqeust;
        // webRequest is a part of the request made to the API. It helps in debugging the error.
        // It contains details like the method used, the request URL, and the parameters passed.
        // This information can be useful for identifying the issue and resolving it.
        // It's a good practice to include this information in error responses
        // to help developers and clients understand the problem better.
        // In the provided code, this information is'
    }



   }



