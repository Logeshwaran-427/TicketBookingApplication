package com.example.TicketBookingApplication.Exceptions;


public class BadRequestException extends RuntimeException {

    public BadRequestException(String message){
        super(message);
    }
    
}
