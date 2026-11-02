package com.example.TicketBookingApplication.DTOs;

import java.time.LocalDate;

import com.example.TicketBookingApplication.Enums.MovieCategory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MovieRequestDTO {


    @NotBlank(message = "Please enter the movie title")
    String title;
    @NotBlank(message = "Please enter the movie description")
    String description;
    @NotNull (message = "Please enter the movie duration")
    @Positive(message = "Duration must be greater than zero")
    Integer duration;
    @NotNull (message = "Please choose the category")
    @Enumerated (EnumType.STRING)
    MovieCategory category;
    String posterUrl;
    @NotBlank(message = "Please enter the movie language")
    String language;
    @NotBlank(message = "Please enter the movie censorRating")
    String censorRating;
    @NotNull (message = "Please enter the releaseDate")
    LocalDate releaseDate;

    public MovieRequestDTO() {
    }

    public MovieRequestDTO(String title, String description, Integer duration, MovieCategory category, String posterUrl,
            String language, String censorRating, LocalDate releaseDate) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.category = category;
        this.posterUrl = posterUrl;
        this.language = language;
        this.censorRating = censorRating;
        this.releaseDate = releaseDate;
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
        return language;
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
