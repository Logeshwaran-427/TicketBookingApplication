package com.example.TicketBookingApplication.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketBookingApplication.DTOs.MovieDetailsResponseDTO;
import com.example.TicketBookingApplication.DTOs.PaginatedMovieResponse;
import com.example.TicketBookingApplication.DTOs.SeatMapResponseDTO;
import com.example.TicketBookingApplication.Enums.MovieCategory;
import com.example.TicketBookingApplication.Service.MovieService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController 
@RequestMapping ("/movies")
public class MovieController {
    
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService=movieService;
    }


    @GetMapping
    public ResponseEntity<PaginatedMovieResponse> getMovie(Pageable pageable) {
        return movieService.getMovies(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedMovieResponse> getMovieByTitle(@RequestParam String movieTitle, Pageable pageable) {
        return movieService.getMoviesByTitle(pageable, movieTitle);
    }

    @GetMapping("/category")
    public ResponseEntity<PaginatedMovieResponse> getMovieByCategory(@RequestParam MovieCategory category, Pageable pageable) {
        return movieService.getMoviesByCategory(pageable, category);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailsResponseDTO> getMovieById(@PathVariable Long id) {
        return movieService.getMoviesById(id);
    }

    @GetMapping ("/shows/{showID}")
    public  ResponseEntity<List<SeatMapResponseDTO>> getShowSeats(@PathVariable  Long showID){
        return  movieService.getShowSeats(showID);
    }
    
    
    


    

}
