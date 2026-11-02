package com.example.TicketBookingApplication.Entities;

import java.time.LocalDateTime;


import com.example.TicketBookingApplication.Enums.ShowSeatStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity 
public class ShowSeat {


    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne 
    @JoinColumn (name = "showId")
    Shows shows;

    @ManyToOne 
    @JoinColumn (name = "seatId")
    Seats seats;

    @Enumerated (EnumType.STRING)
    ShowSeatStatus seatStatus;

    Double price;

    LocalDateTime heldUntil;

    @Version 
    Long version;

    public ShowSeat() {
    }

    public ShowSeat(Long id, Shows shows, Seats seats, ShowSeatStatus seatStatus, LocalDateTime heldUntil, Double price) {
        this.id = id;
        this.shows = shows;
        this.seats = seats;
        this.seatStatus = seatStatus;
        this.heldUntil = heldUntil;
        this.price=price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shows getShows() {
        return shows;
    }

    public void setShows(Shows shows) {
        this.shows = shows;
    }

    public Seats getSeats() {
        return seats;
    }

    public void setSeats(Seats seats) {
        this.seats = seats;
    }

    public ShowSeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(ShowSeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public LocalDateTime getHeldUntil() {
        return heldUntil;
    }

    public void setHeldUntil(LocalDateTime heldUntil) {
        this.heldUntil = heldUntil;
    }

    public Long getVersion() {
        return version;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}
