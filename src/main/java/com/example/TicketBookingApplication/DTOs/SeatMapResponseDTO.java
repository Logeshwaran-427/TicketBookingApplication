package com.example.TicketBookingApplication.DTOs;

import com.example.TicketBookingApplication.Enums.SeatType;
import com.example.TicketBookingApplication.Enums.ShowSeatStatus;

public class SeatMapResponseDTO {

    Long showSeatId;
    String seatNumber;
    SeatType seatType;
    ShowSeatStatus seatStatus;
    Double price;

    public SeatMapResponseDTO() {
    }

    public SeatMapResponseDTO(Long showSeatId, String seatNumber, SeatType seatType, ShowSeatStatus seatStatus,
            Double price) {
        this.showSeatId = showSeatId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatStatus = seatStatus;
        this.price = price;
    }
    
    public Long getShowSeatId() {
        return showSeatId;
    }
    public void setShowSeatId(Long showSeatId) {
        this.showSeatId = showSeatId;
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
    public ShowSeatStatus getSeatStatus() {
        return seatStatus;
    }
    public void setSeatStatus(ShowSeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    
    
}
