package com.example.TicketBookingApplication.Entities;

import java.time.LocalDate;

import com.example.TicketBookingApplication.Enums.MovieCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity 
public class Movie {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;
    Integer duration;

    @Enumerated (EnumType.STRING)
    MovieCategory category;
    String posterUrl;
    String language;
    String censorRating;
    LocalDate releaseDate;

    public Movie(Long id, String title, String description, Integer duration, MovieCategory category, String posterUrl,
            String language, String censorRating, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.category = category;
        this.posterUrl = posterUrl;
        this.language = language;
        this.censorRating = censorRating;
        this.releaseDate = releaseDate;
    }

    public Movie() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public MovieCategory getCategory() {
        return category;
    }
    public void setCategory(MovieCategory category) {
        this.category = category;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public String getLanguage() {
        return this.language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getCensorRating() {
        return censorRating;
    }
    public void setCensorRating(String censorRating) {
        this.censorRating = censorRating;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
}
