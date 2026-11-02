package com.example.TicketBookingApplication.ServiceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.TicketBookingApplication.DTOs.MovieRequestDTO;
import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Enums.MovieCategory;
import com.example.TicketBookingApplication.Repository.MovieRepository;
import com.example.TicketBookingApplication.Service.MovieService;

@ExtendWith (MockitoExtension.class)
public class MovieServiceTest {
    
    @Mock 
    MovieRepository movieRepository;

    @InjectMocks 
    MovieService movieService;

    @Test 
    public void testAddMovie(){

        MovieRequestDTO movie1=new MovieRequestDTO();
        movie1.setCategory(MovieCategory.ACTION);
        movie1.setCensorRating("A");
        movie1.setDescription("Action flim");
        movie1.setDuration(230);
        movie1.setLanguage("English");
        movie1.setPosterUrl("gvuhbio");
        movie1.setReleaseDate(LocalDate.of(2026, 12, 12));
        movie1.setTitle("Harry");

        
        ResponseEntity<String> response=movieService.addMovie(movie1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Movie Details Added");

        verify(movieRepository).save(any(Movie.class));

    }
    

}
