package com.example.TicketBookingApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.Venue;

public interface VenueRepository extends JpaRepository<Venue,Long>{

        Optional<Venue> findByName(String name);
    
}
