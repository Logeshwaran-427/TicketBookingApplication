package com.example.TicketBookingApplication.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Enums.MovieCategory;

 
public interface MovieRepository extends JpaRepository<Movie,Long> {
    
    Page<Movie> findAll(Pageable pageable);
    
    Page<Movie> findByTitle(Pageable pageable, String movieTitle);
    
    Page<Movie> findByCategory(Pageable pageable, MovieCategory category);

    Optional<Movie> findByTitle(String title);

}
