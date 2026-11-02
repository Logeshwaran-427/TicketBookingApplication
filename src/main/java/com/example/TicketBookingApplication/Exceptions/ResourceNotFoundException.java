package com.example.TicketBookingApplication.Exceptions;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg){
        super(msg);
    }
    
}
