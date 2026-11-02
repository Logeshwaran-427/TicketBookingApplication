package com.example.TicketBookingApplication.DTOs;

import java.util.List;

public class SeatLayoutRequestDTO {
    
    List<RowConfig> rows;

    public SeatLayoutRequestDTO(List<RowConfig> rows) {
        this.rows = rows;
    }

    public SeatLayoutRequestDTO() {
    }

    public List<RowConfig> getRows() {
        return rows;
    }

    public void setRows(List<RowConfig> rows) {
        this.rows = rows;
    }

}
