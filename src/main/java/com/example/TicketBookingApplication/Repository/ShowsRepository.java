package com.example.TicketBookingApplication.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Entities.Shows;
import com.example.TicketBookingApplication.Entities.Venue;

public interface ShowsRepository extends JpaRepository<Shows,Long> {


    boolean existsByMovieAndVenueAndShowStarTime(Movie movie, Venue venue, LocalDateTime showStartTime);
    
    boolean existsByMovieAndVenue(Movie movie, Venue venue);

    List<Shows> findByMovieAndVenue(Movie movie,Venue venue);

    Optional<Shows> findByVenue(Venue venue);
}
