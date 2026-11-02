package com.example.TicketBookingApplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.BookingSeat;
import com.example.TicketBookingApplication.Entities.Bookings;
import com.example.TicketBookingApplication.Entities.ShowSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat,Long> {

    List<BookingSeat> findAllByBookings(Bookings bookings);

    List<BookingSeat> findAllByShowSeatIn(List<ShowSeat> showSeats);

    
}
