package com.example.TicketBookingApplication.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import com.example.TicketBookingApplication.Enums.BookingStatus;

public class BookingResponseDTO {

    //  { id, movieTitle, showDateTime, seats: List<String>, status, totalAmount, bookingTime }

    Long bookingId;
    String movieTitle;
    LocalDateTime showDateTime;
    List<String> seats;
    BookingStatus status;
    Double totalAmount;
    LocalDateTime bookingTime;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(Long bookingId, String movieTitle, LocalDateTime showDateTime, List<String> seats,
            BookingStatus status, Double totalAmount, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.movieTitle = movieTitle;
        this.showDateTime = showDateTime;
        this.seats = seats;
        this.status = status;
        this.totalAmount = totalAmount;
        this.bookingTime = bookingTime;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }
    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }
    public List<String> getSeats() {
        return seats;
    }
    public void setSeats(List<String> seats) {
        this.seats = seats;
    }
    public BookingStatus getStatus() {
        return status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    public Double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
}
