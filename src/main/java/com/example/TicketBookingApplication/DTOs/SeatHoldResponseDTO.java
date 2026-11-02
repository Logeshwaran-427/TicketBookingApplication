package com.example.TicketBookingApplication.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class SeatHoldResponseDTO {

    Long bookingId;
    LocalDateTime heldExpiresAt;
    Double totalAmount;
    List<String> heldSeats;

    public SeatHoldResponseDTO(Long bookingId, LocalDateTime heldExpiresAt, Double totalAmount,
            List<String> heldSeats) {
        this.bookingId = bookingId;
        this.heldExpiresAt = heldExpiresAt;
        this.totalAmount = totalAmount;
        this.heldSeats = heldSeats;
    }

    public SeatHoldResponseDTO() {
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public LocalDateTime getHeldExpiresAt() {
        return heldExpiresAt;
    }
    public void setHeldExpiresAt(LocalDateTime heldExpiresAt) {
        this.heldExpiresAt = heldExpiresAt;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public List<String> getHeldSeats() {
        return heldSeats;
    }
    public void setHeldSeats(List<String> heldSeats) {
        this.heldSeats = heldSeats;
    }

    
    
}
