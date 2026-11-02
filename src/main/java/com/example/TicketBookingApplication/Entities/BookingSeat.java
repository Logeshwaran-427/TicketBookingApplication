package com.example.TicketBookingApplication.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class BookingSeat {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    Double price;

    @ManyToOne 
    @JoinColumn (name = "bookingId")
    Bookings bookings;
    
    @ManyToOne 
    @JoinColumn (name = "showSeatId")
    ShowSeat showSeat;

    public BookingSeat() {
    }

    public BookingSeat(Long id, Double price, Bookings bookings, ShowSeat showSeat) {
        this.id = id;
        this.price = price;
        this.bookings = bookings;
        this.showSeat = showSeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Bookings getBookings() {
        return bookings;
    }

    public void setBookings(Bookings bookings) {
        this.bookings = bookings;
    }

    public ShowSeat getShowSeat() {
        return showSeat;
    }

    public void setShowSeat(ShowSeat showSeat) {
        this.showSeat = showSeat;
    }


}
