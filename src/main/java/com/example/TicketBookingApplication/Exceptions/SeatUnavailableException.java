package com.example.TicketBookingApplication.Exceptions;

public class SeatUnavailableException extends RuntimeException {
    public SeatUnavailableException(String msg){
        super(msg);
    }
}
