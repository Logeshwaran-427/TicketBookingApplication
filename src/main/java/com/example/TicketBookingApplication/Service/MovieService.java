package com.example.TicketBookingApplication.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.TicketBookingApplication.DTOs.MovieDetailsResponseDTO;
import com.example.TicketBookingApplication.DTOs.MovieRequestDTO;
import com.example.TicketBookingApplication.DTOs.PaginatedMovieResponse;
import com.example.TicketBookingApplication.DTOs.SeatMapResponseDTO;
import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Entities.ShowSeat;
import com.example.TicketBookingApplication.Entities.Shows;
import com.example.TicketBookingApplication.Enums.MovieCategory;
import com.example.TicketBookingApplication.Enums.SeatType;
import com.example.TicketBookingApplication.Exceptions.ResourceNotFoundException;
import com.example.TicketBookingApplication.Repository.MovieRepository;
import com.example.TicketBookingApplication.Repository.ShowSeatsRepository;
import com.example.TicketBookingApplication.Repository.ShowsRepository;

@Service 
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowSeatsRepository showSeatsRepository;
    private final ShowsRepository showsRepository;

    public MovieService(MovieRepository movieRepository,ShowSeatsRepository showSeatsRepository,ShowsRepository showsRepository){
        this.movieRepository=movieRepository;
        this.showSeatsRepository=showSeatsRepository;
        this.showsRepository=showsRepository;
    }
    

    public ResponseEntity<String> addMovie(MovieRequestDTO movieRequestDTO){

        Movie movie=new Movie();
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setCategory(movieRequestDTO.getCategory());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setLanguage(movieRequestDTO.getLanguage());
        movie.setCensorRating(movieRequestDTO.getCensorRating());
        movie.setDuration(movieRequestDTO.getDuration());
        movie.setPosterUrl(movieRequestDTO.getPosterUrl());
        movie.setReleaseDate(movieRequestDTO.getReleaseDate());

        movieRepository.save(movie);

        return ResponseEntity.status(HttpStatus.CREATED).body("Movie Details Added");

    }


    public ResponseEntity<PaginatedMovieResponse> getMovies(Pageable pageable){

        Page<Movie> movies=movieRepository.findAll(pageable);
        List<MovieDetailsResponseDTO> movieResponses=new ArrayList<>();

        for(Movie m:movies){
            MovieDetailsResponseDTO response=new MovieDetailsResponseDTO();
            response.setCategory(m.getCategory());
            response.setCensorRating(m.getCensorRating());
            response.setDescription(m.getDescription());
            response.setDuration(m.getDuration());
            response.setLanguage(m.getLanguage());
            response.setPosterUrl(m.getPosterUrl());
            response.setReleaseDate(m.getReleaseDate());
            response.setTitle(m.getTitle());
            movieResponses.add(response);
        }

        PaginatedMovieResponse paginatedMovieResponse=new PaginatedMovieResponse(movieResponses,movies.getNumber(),movies.getTotalPages());

        
        return ResponseEntity.status(HttpStatus.OK).body(paginatedMovieResponse);
    }


    public ResponseEntity<PaginatedMovieResponse> getMoviesByTitle(Pageable pageable, String movieTitle){
        Page<Movie> movies=movieRepository.findByTitle(pageable, movieTitle);

        List<MovieDetailsResponseDTO> movieResponses=new ArrayList<>();

        for(Movie m:movies){
            MovieDetailsResponseDTO response=new MovieDetailsResponseDTO();
            response.setCategory(m.getCategory());
            response.setCensorRating(m.getCensorRating());
            response.setDescription(m.getDescription());
            response.setDuration(m.getDuration());
            response.setLanguage(m.getLanguage());
            response.setPosterUrl(m.getPosterUrl());
            response.setReleaseDate(m.getReleaseDate());
            response.setTitle(m.getTitle());
            movieResponses.add(response);
        }


        PaginatedMovieResponse paginatedMovieResponse=new PaginatedMovieResponse(movieResponses,movies.getNumber(),movies.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(paginatedMovieResponse);
    }


    public ResponseEntity<PaginatedMovieResponse> getMoviesByCategory(Pageable pageable, MovieCategory category){
        Page<Movie> movies=movieRepository.findByCategory(pageable, category);

        List<MovieDetailsResponseDTO> movieResponses=new ArrayList<>();

        for(Movie m:movies){
            MovieDetailsResponseDTO response=new MovieDetailsResponseDTO();
            response.setCategory(m.getCategory());
            response.setCensorRating(m.getCensorRating());
            response.setDescription(m.getDescription());
            response.setDuration(m.getDuration());
            response.setLanguage(m.getLanguage());
            response.setPosterUrl(m.getPosterUrl());
            response.setReleaseDate(m.getReleaseDate());
            response.setTitle(m.getTitle());
            movieResponses.add(response);
        }


        PaginatedMovieResponse paginatedMovieResponse=new PaginatedMovieResponse(movieResponses,movies.getNumber(),movies.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(paginatedMovieResponse);
    }


    public ResponseEntity<MovieDetailsResponseDTO> getMoviesById( Long movieId){

        Movie m=movieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("Movie Not Found"));

        MovieDetailsResponseDTO response=new MovieDetailsResponseDTO();
            response.setCategory(m.getCategory());
            response.setCensorRating(m.getCensorRating());
            response.setDescription(m.getDescription());
            response.setDuration(m.getDuration());
            response.setLanguage(m.getLanguage());
            response.setPosterUrl(m.getPosterUrl());
            response.setReleaseDate(m.getReleaseDate());
            response.setTitle(m.getTitle());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    public ResponseEntity<List<SeatMapResponseDTO>> getShowSeats(Long showId){

        Shows show=showsRepository.findById(showId).orElseThrow(()->new ResourceNotFoundException("Show not found"));

        List<ShowSeat> showSeat=showSeatsRepository.findAllByShows(show);

        List<SeatMapResponseDTO> seatMapResponseDTOs=new ArrayList<>();

        for(ShowSeat ss: showSeat){
            SeatMapResponseDTO seatMapResponseDTO=new SeatMapResponseDTO();
            seatMapResponseDTO.setShowSeatId(ss.getId());
            seatMapResponseDTO.setSeatNumber(ss.getSeats().getSeatNumber());
            seatMapResponseDTO.setSeatStatus(ss.getSeatStatus());
            seatMapResponseDTO.setPrice(ss.getPrice());
            seatMapResponseDTO.setSeatType(ss.getSeats().getSeatType());
            seatMapResponseDTOs.add(seatMapResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(seatMapResponseDTOs);

    }

    




    
}
