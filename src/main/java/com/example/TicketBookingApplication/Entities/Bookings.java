package com.example.TicketBookingApplication.Entities;

import java.time.LocalDateTime;

import com.example.TicketBookingApplication.Enums.BookingStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Bookings {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY) 
    Long id;
    Long userId;
    @ManyToOne 
    @JoinColumn (name = "showId")
    Shows shows;
    LocalDateTime bookingTime;
    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;
    Double totalAmount;

    public Bookings(Long id, Long userId, Shows shows, LocalDateTime bookingTime, BookingStatus bookingStatus,
            Double totalAmount) {
        this.id = id;
        this.userId = userId;
        this.shows = shows;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
        this.totalAmount = totalAmount;
    }

    public Bookings() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Shows getShows() {
        return shows;
    }
    public void setShows(Shows shows) {
        this.shows = shows;
    }
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    

    
}
