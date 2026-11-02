package com.example.TicketBookingApplication.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketBookingApplication.DTOs.BookingResponseDTO;
import com.example.TicketBookingApplication.DTOs.SeatHoldRequestDTO;
import com.example.TicketBookingApplication.DTOs.SeatHoldResponseDTO;
import com.example.TicketBookingApplication.Service.BookingService;


@RestController 
@RequestMapping ("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }

    @PreAuthorize ("hasRole('MEMBER')")
    @PostMapping("/hold")
    public ResponseEntity<SeatHoldResponseDTO> holdSeats(@RequestBody SeatHoldRequestDTO seatHoldRequestDTO) {
        return bookingService.holdSeat(seatHoldRequestDTO);
    }

    @PreAuthorize ("hasRole('MEMBER')")
    @PostMapping("/confirm/{id}")
    public ResponseEntity<BookingResponseDTO> confirmSeats(@PathVariable Long id) {
        return bookingService.confirmBooking(id);
    }
    
    @PreAuthorize ("hasRole('MEMBER')")
    @DeleteMapping ("/cancel/{id}")
    public ResponseEntity<String> cancelSeats(@PathVariable Long id){
        return bookingService.cancelBooking(id);
    }
    
    
}
