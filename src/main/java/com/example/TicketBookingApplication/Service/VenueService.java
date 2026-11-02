package com.example.TicketBookingApplication.Service;

import com.example.TicketBookingApplication.Repository.ShowSeatsRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.TicketBookingApplication.DTOs.RowConfig;
import com.example.TicketBookingApplication.DTOs.SeatLayoutRequestDTO;
import com.example.TicketBookingApplication.DTOs.SeatResponseDTO;
import com.example.TicketBookingApplication.DTOs.ShowRequestDTO;
import com.example.TicketBookingApplication.DTOs.UserContext;
import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Entities.Seats;
import com.example.TicketBookingApplication.Entities.ShowSeat;
import com.example.TicketBookingApplication.Entities.Shows;
import com.example.TicketBookingApplication.Entities.Venue;
import com.example.TicketBookingApplication.Enums.SeatType;
import com.example.TicketBookingApplication.Enums.ShowSeatStatus;
import com.example.TicketBookingApplication.Exceptions.BadRequestException;
import com.example.TicketBookingApplication.Exceptions.ResourceNotFoundException;
import com.example.TicketBookingApplication.Repository.MovieRepository;
import com.example.TicketBookingApplication.Repository.SeatRepository;
import com.example.TicketBookingApplication.Repository.ShowsRepository;
import com.example.TicketBookingApplication.Repository.VenueRepository;

@Service 
public class VenueService {

    private final ShowSeatsRepository showSeatsRepository;
    private final VenueRepository venueRepository;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;
    private final ShowsRepository showsRepository;

    public VenueService(VenueRepository venueRepository, SeatRepository seatRepository, MovieRepository movieRepository,ShowsRepository showsRepository, ShowSeatsRepository showSeatsRepository){
        this.venueRepository=venueRepository;
        this.seatRepository=seatRepository;
        this.movieRepository=movieRepository;
        this.showsRepository=showsRepository;
        this.showSeatsRepository = showSeatsRepository;
    }

    public ResponseEntity<List<SeatResponseDTO>> seatCreation(Long venueId,SeatLayoutRequestDTO seatLayoutRequestDTO){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext=(UserContext) auth.getDetails();
        Long userId=userContext.getUserId();

        Venue venue=venueRepository.findById(venueId).orElseThrow(()-> new ResourceNotFoundException("Venue Not Found!!"));

        if(!venue.getOwnerId().equals(userId)){
            throw new BadRequestException("You are not the owner for this venue. Please enter your venue ID");
        }


        List<Seats> seats=new ArrayList<>();

        for(RowConfig row:seatLayoutRequestDTO.getRows() ){
            for(int i=1;i<=row.getSeatCount();i++){
                Seats seat=new Seats();
                seat.setSeatNumber(row.getRowLabel()+i);
                seat.setSeatType(row.getSeatType());
                seat.setVenue(venue);
                seats.add(seat);
            }
        }

        List<Seats> saved=seatRepository.saveAll(seats);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.stream().map(seat->mapToDTO(seat)).toList());
    }

    public SeatResponseDTO mapToDTO(Seats seats){

        SeatResponseDTO seatResponse=new SeatResponseDTO();
        seatResponse.setId(seats.getId());
        seatResponse.setSeatNumber(seats.getSeatNumber());
        seatResponse.setSeatType(seats.getSeatType());
        
        return seatResponse;
    }


    public ResponseEntity<String> addShow(ShowRequestDTO showRequestDTO){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext=(UserContext) auth.getDetails();
        Long userId=userContext.getUserId();

        Venue venue=venueRepository.findById(showRequestDTO.getVenueId()).orElseThrow(()-> new ResourceNotFoundException("Venue Not Found!!"));

        if(!venue.getOwnerId().equals(userId)){
            throw new BadRequestException("You are not the owner for this venue. Please enter your venue ID");
        }

        Movie movie=movieRepository.findById(showRequestDTO.getMovieId()).orElseThrow(()-> new ResourceNotFoundException("Movie Not Found"));

        List<Seats> seats=seatRepository.findByVenue(venue);
        Set<SeatType> seatTypesIntheatre=seats.stream().map(seat->seat.getSeatType()).collect(Collectors.toSet());

        for(SeatType sType: seatTypesIntheatre){
            if(!showRequestDTO.getPrice().containsKey(sType)){
                throw new BadRequestException("Missing price for the seat type "+ sType);
            }
        }

        if(showsRepository.existsByMovieAndVenueAndShowStarTime(movie, venue, showRequestDTO.getShowDateTime())){
            throw new BadRequestException("Show has been already added for this movie at this time");
        }

        // if(showsRepository.existsByMovieAndVenue(movie, venue)){
        //      List<Shows> shows=showsRepository.findByMovieAndVenue(movie, venue);
        //      Integer movieDuration=movie.getDuration();
        //     if(!shows.isEmpty()){
        //         for(Shows show:shows){
        //             LocalDateTime alreadyAddedShowTime=show.getShowStarTime();
        //             Long difference=Duration.between(alreadyAddedShowTime, showRequestDTO.getShowDateTime()).toMinutes();
        //             if(movieDuration.equals(difference)){
        //                 throw new BadRequestException("You are adding the show before completing the previous show");
        //             }
        //         }
        //     }
        // }
        

        Shows shows=new Shows();
        shows.setMovie(movie);
        shows.setVenue(venue);
        shows.setShowStarTime(showRequestDTO.getShowDateTime());
        showsRepository.save(shows);

        

        List<ShowSeat> showSeats=new ArrayList<>();

        for(Seats seat: seats){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setSeats(seat);
            showSeat.setShows(shows);
            showSeat.setSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeat.setPrice(showRequestDTO.getPrice().get(seat.getSeatType()));
            showSeats.add(showSeat);
        }

        showSeatsRepository.saveAll(showSeats);
        
        

        return ResponseEntity.status(HttpStatus.CREATED).body("Shows and seats for the shows has been created");

    }
    
}
