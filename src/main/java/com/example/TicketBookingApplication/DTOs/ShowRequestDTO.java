package com.example.TicketBookingApplication.DTOs;

import java.time.LocalDateTime;
import java.util.Map;

import com.example.TicketBookingApplication.Enums.SeatType;

public class ShowRequestDTO {

    Long movieId;
    Long venueId;
    LocalDateTime showDateTime;
    Map<SeatType,Double> price;

    

    public ShowRequestDTO(Long movieId, Long venueId, LocalDateTime showDateTime, Map<SeatType,Double> price) {
        this.movieId = movieId;
        this.venueId = venueId;
        this.showDateTime = showDateTime;
        this.price=price;
    }

    public ShowRequestDTO() {
    }
    
    public Long getMovieId() {
        return movieId;
    }
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    public Long getVenueId() {
        return venueId;
    }
    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }
    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }

    public Map<SeatType, Double> getPrice() {
        return price;
    }

    public void setPrice(Map<SeatType, Double> price) {
        this.price = price;
    }
    
    
}
