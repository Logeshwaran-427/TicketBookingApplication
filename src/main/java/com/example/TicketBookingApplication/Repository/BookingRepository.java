package com.example.TicketBookingApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.Bookings;

public interface BookingRepository extends JpaRepository<Bookings,Long>{
    
}
