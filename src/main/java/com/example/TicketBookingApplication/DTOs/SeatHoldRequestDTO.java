package com.example.TicketBookingApplication.DTOs;

import java.util.List;

import com.example.TicketBookingApplication.Entities.Seats;

public class SeatHoldRequestDTO {
    
    Long showId;
    List<Long> seats;

    public SeatHoldRequestDTO(Long showId, List<Long> seats) {
        this.showId = showId;
        this.seats = seats;
    }

    public SeatHoldRequestDTO() {
    }
    
    public Long getShowId() {
        return showId;
    }
    public void setShowId(Long showId) {
        this.showId = showId;
    }
    public List<Long> getSeats() {
        return seats;
    }
    public void setSeats(List<Long> seats) {
        this.seats = seats;
    }

    

}
