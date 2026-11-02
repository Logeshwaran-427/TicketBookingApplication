package com.example.TicketBookingApplication.DTOs;

import com.example.TicketBookingApplication.Enums.SeatType;

public class SeatResponseDTO {

    Long id;
    String seatNumber;
    SeatType seatType;

    public SeatResponseDTO() {
    }

    public SeatResponseDTO(Long id, String seatNumber, SeatType seatType) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSeatNumber() {
        return seatNumber;
    }
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    public SeatType getSeatType() {
        return seatType;
    }
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    
    
}
