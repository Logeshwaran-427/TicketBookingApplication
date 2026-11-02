package com.example.TicketBookingApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.Seats;
import com.example.TicketBookingApplication.Entities.Venue;

public interface SeatRepository extends JpaRepository<Seats,Long> {

    List<Seats> findByVenue(Venue venue);
    
}
