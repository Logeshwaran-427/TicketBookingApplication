package com.example.TicketBookingApplication.DTOs;

import com.example.TicketBookingApplication.Enums.SeatType;

public class RowConfig {

    String rowLabel;
    Integer seatCount;
    SeatType seatType;

    public RowConfig(String rowLabel, Integer seatCount, SeatType seatType) {
        this.rowLabel = rowLabel;
        this.seatCount = seatCount;
        this.seatType = seatType;
    }

    public RowConfig() {
    }
    
    public String getRowLabel() {
        return rowLabel;
    }
    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }
    public Integer getSeatCount() {
        return seatCount;
    }
    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }
    public SeatType getSeatType() {
        return seatType;
    }
    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }
    
}
