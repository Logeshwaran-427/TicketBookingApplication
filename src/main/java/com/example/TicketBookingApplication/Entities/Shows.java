package com.example.TicketBookingApplication.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Shows {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne 
    @JoinColumn (name = "venueId")
    Venue venue;

    @ManyToOne 
    @JoinColumn (name = "Movie_id")
    Movie movie;

    LocalDateTime showStarTime;
    public Shows(Long id, Venue venue, Movie movie, LocalDateTime showStarTime) {
        this.id = id;
        this.venue = venue;
        this.movie = movie;
        this.showStarTime = showStarTime;
    }
    public Shows() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public LocalDateTime getShowStarTime() {
        return showStarTime;
    }
    public void setShowStarTime(LocalDateTime showStarTime) {
        this.showStarTime = showStarTime;
    }

    
    
}
