package com.example.TicketBookingApplication.Entities;

import com.example.TicketBookingApplication.Enums.SeatType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Seats {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    String seatNumber;

    @Enumerated (EnumType.STRING)
    SeatType seatType;

    @ManyToOne
    @JoinColumn (name = "venueId")
    Venue venue;

    public Seats(Long id, String seatNumber, SeatType seatType, Venue venue) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.venue = venue;
    }

    public Seats() {
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

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    
    
}
