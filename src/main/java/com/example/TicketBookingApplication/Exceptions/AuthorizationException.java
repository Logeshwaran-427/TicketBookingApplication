package com.example.TicketBookingApplication.Exceptions;

public class AuthorizationException extends RuntimeException{
    
    public AuthorizationException(String message){
        super(message);
    }

}
