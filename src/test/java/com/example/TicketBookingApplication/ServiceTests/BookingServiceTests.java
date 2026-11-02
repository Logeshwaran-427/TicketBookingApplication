package com.example.TicketBookingApplication.ServiceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.TicketBookingApplication.DTOs.BookingResponseDTO;
import com.example.TicketBookingApplication.DTOs.SeatHoldRequestDTO;
import com.example.TicketBookingApplication.DTOs.SeatHoldResponseDTO;
import com.example.TicketBookingApplication.DTOs.UserContext;
import com.example.TicketBookingApplication.Entities.BookingSeat;
import com.example.TicketBookingApplication.Entities.Bookings;
import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Entities.Seats;
import com.example.TicketBookingApplication.Entities.ShowSeat;
import com.example.TicketBookingApplication.Entities.Shows;
import com.example.TicketBookingApplication.Entities.Venue;
import com.example.TicketBookingApplication.Enums.BookingStatus;
import com.example.TicketBookingApplication.Enums.MovieCategory;
import com.example.TicketBookingApplication.Enums.ShowSeatStatus;
import com.example.TicketBookingApplication.Exceptions.ResourceNotFoundException;
import com.example.TicketBookingApplication.Repository.BookingRepository;
import com.example.TicketBookingApplication.Repository.BookingSeatRepository;
import com.example.TicketBookingApplication.Repository.ShowSeatsRepository;
import com.example.TicketBookingApplication.Repository.ShowsRepository;
import com.example.TicketBookingApplication.Service.BookingService;

@ExtendWith (MockitoExtension.class)
public class BookingServiceTests {
    
    @Mock 
    BookingRepository bookingRepository;

    @Mock 
    BookingSeatRepository bookingSeatRepository;

    @Mock 
    ShowSeatsRepository showSeatsRepository;

    @Mock 
    ShowsRepository showsRepository;

    @InjectMocks 
    BookingService bookingService;

    @Test 
    public void  testheldTickets(){

        UserContext userContext=new UserContext();
        userContext.setUserId(1l);

        Authentication authentication=mock(Authentication.class);
        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);


        List<Long> seats=List.of(1l,2l,3l);

        SeatHoldRequestDTO seatHoldRequestDTO=new SeatHoldRequestDTO();
        seatHoldRequestDTO.setShowId(1L);
        seatHoldRequestDTO.setSeats(seats);

        Shows show=new Shows();
        show.setId(seatHoldRequestDTO.getShowId());

        when(showsRepository.findById(seatHoldRequestDTO.getShowId())).thenReturn(Optional.of(show));

        List<ShowSeat> showSeats=new ArrayList<>();
        for(Long id:seats){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setId(id);
            showSeat.setShows(show);
            showSeat.setSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeat.setPrice(100.0);
            Seats seat = new Seats();
            seat.setSeatNumber("A" + id);

            showSeat.setSeats(seat);
            showSeats.add(showSeat);
        }


        when(showSeatsRepository.findAllById(seatHoldRequestDTO.getSeats())).thenReturn(showSeats);
        for(ShowSeat s:showSeats){
        when(showSeatsRepository.existsByIdAndShowsAndSeatStatus(s.getId(), s.getShows(), ShowSeatStatus.AVAILABLE)).thenReturn(true);
        }

        when(bookingRepository.save(any(Bookings.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<SeatHoldResponseDTO> response=bookingService.holdSeat(seatHoldRequestDTO);

        


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        SeatHoldResponseDTO body=response.getBody();
        assertThat(body.getHeldSeats()).hasSize(3);
        assertThat(body).isNotNull();
        assertThat(body.getTotalAmount()).isEqualTo(300.0);

        ArgumentCaptor<Bookings> bookingCaptor=ArgumentCaptor.forClass(Bookings.class);
        verify(bookingRepository,times(2)).save(bookingCaptor.capture());
        List<Bookings> bookings=bookingCaptor.getAllValues();

        assertThat(bookings).hasSize(2);

        Bookings booking=bookings.get(0);

        assertThat(booking.getUserId()).isEqualTo(1L);
        assertThat(booking.getBookingStatus()).isEqualTo(BookingStatus.PAYMENT_PENDING);
        assertThat(booking.getShows()).isEqualTo(show);


        ArgumentCaptor<ShowSeat> seatCaptor=ArgumentCaptor.forClass(ShowSeat.class);
        verify(showSeatsRepository,times(3)).save(seatCaptor.capture());

        List<ShowSeat> showSeats2=seatCaptor.getAllValues();
        assertThat(showSeats2).hasSize(3);

        for(ShowSeat seat:showSeats2){
            assertThat(seat.getSeatStatus()).isEqualTo(ShowSeatStatus.HELD);
            assertThat(seat.getHeldUntil()).isNotNull();
        }

        verify(bookingSeatRepository).saveAll(anyList());
        
    }


    @Test 
    public void testConfirmTickets(){

        UserContext userContext=new UserContext();
        userContext.setUserId(1l);

        Authentication authentication=mock(Authentication.class);
        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        Long bookingId=1l;

        Movie movie=new Movie();
        movie.setCategory(MovieCategory.ACTION);
        movie.setCensorRating("U");
        movie.setDescription("Action flim");
        movie.setDuration(129);
        movie.setLanguage("tamil");
        movie.setPosterUrl("szdxfcgvhbjnkm");
        movie.setReleaseDate(LocalDate.of(2026, 12, 2));
        movie.setTitle("Jailer");

        Venue venue=new Venue();
        venue.setLocation("Erode");
        venue.setName("Vel");
        venue.setOwnerId(3l);

        Shows shows=new Shows();
        shows.setMovie(movie);
        shows.setShowStarTime(LocalDateTime.now());
        shows.setVenue(venue);

        List<Long> seats=List.of(1l,2l,3l);

        List<ShowSeat> showSeats=new ArrayList<>();
        for(Long id:seats){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setId(id);
            showSeat.setShows(shows);
            showSeat.setSeatStatus(ShowSeatStatus.HELD);
            showSeat.setPrice(100.0);
            showSeat.setHeldUntil(LocalDateTime.now().plusMinutes(5));
            Seats seat = new Seats();
            seat.setSeatNumber("A" + id);

            showSeat.setSeats(seat);
            showSeats.add(showSeat);
        }

    

        Bookings bookings=new Bookings();
        bookings.setId(bookingId);
        bookings.setBookingStatus(BookingStatus.PAYMENT_PENDING);
        bookings.setShows(shows);
        bookings.setTotalAmount(300.0);
        bookings.setUserId(1l);
        bookings.setBookingTime(LocalDateTime.now());

        List<BookingSeat> bSeats=new ArrayList<>();

        for(ShowSeat ss:showSeats){

            BookingSeat seat1=new BookingSeat();
            seat1.setBookings(bookings);
            seat1.setPrice(ss.getPrice());
            seat1.setShowSeat(ss);

            bSeats.add(seat1);

        }


        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(bookings));

        when(bookingSeatRepository.findAllByBookings(bookings)).thenReturn(bSeats);

        ResponseEntity<BookingResponseDTO> response=bookingService.confirmBooking(bookingId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        
        BookingResponseDTO bookingResponse=response.getBody();

        assertThat(bookingResponse.getStatus()).isEqualTo(BookingStatus.CONFIRMED);
        assertThat(bookingResponse.getBookingId()).isEqualTo(bookingId);
        assertThat(bookingResponse.getMovieTitle()).isEqualTo("Jailer");
        assertThat(bookingResponse.getTotalAmount()).isEqualTo(300.0);
        assertThat(bookingResponse.getSeats()).containsExactlyInAnyOrder("A1", "A2", "A3");

        ArgumentCaptor<List<ShowSeat>> showSeatCaptor=ArgumentCaptor.forClass(List.class);

        verify(showSeatsRepository).saveAll(showSeatCaptor.capture());
        List<ShowSeat> showSeats1=showSeatCaptor.getValue();
        assertThat(showSeats1).hasSize(3);
        for(ShowSeat bSeat:showSeats1){
            assertThat(bSeat.getSeatStatus()).isEqualTo(ShowSeatStatus.BOOKED);
        }

        ArgumentCaptor <Bookings> bookingCaptor=ArgumentCaptor.forClass(Bookings.class);
        verify(bookingRepository).save(bookingCaptor.capture());
        Bookings bookings2=bookingCaptor.getValue();

        assertThat(bookings2.getBookingStatus()).isEqualTo(BookingStatus.CONFIRMED);
        
        

    }



    @Test 
    public void testCancelBookingMethod(){

        UserContext userContext=new UserContext();
        userContext.setUserId(1l);

        Authentication authentication=mock(Authentication.class);

        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        Long bookingId=1l;

        Movie movie=new Movie();
        movie.setCategory(MovieCategory.ACTION);
        movie.setCensorRating("U");
        movie.setDescription("Action flim");
        movie.setDuration(129);
        movie.setLanguage("tamil");
        movie.setPosterUrl("szdxfcgvhbjnkm");
        movie.setReleaseDate(LocalDate.of(2026, 12, 2));
        movie.setTitle("Jailer");

        Venue venue=new Venue();
        venue.setLocation("Erode");
        venue.setName("Vel");
        venue.setOwnerId(3l);

        Shows shows=new Shows();
        shows.setMovie(movie);
        shows.setShowStarTime(LocalDateTime.now());
        shows.setVenue(venue);

        List<Long> seats=List.of(1l,2l,3l);

        List<ShowSeat> showSeats=new ArrayList<>();
        for(Long id:seats){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setId(id);
            showSeat.setShows(shows);
            showSeat.setSeatStatus(ShowSeatStatus.HELD);
            showSeat.setPrice(100.0);
            showSeat.setHeldUntil(LocalDateTime.now().plusMinutes(5));
            Seats seat = new Seats();
            seat.setSeatNumber("A" + id);

            showSeat.setSeats(seat);
            showSeats.add(showSeat);
        }

    

        Bookings bookings=new Bookings();
        bookings.setId(bookingId);
        bookings.setBookingStatus(BookingStatus.PAYMENT_PENDING);
        bookings.setShows(shows);
        bookings.setTotalAmount(300.0);
        bookings.setUserId(1l);
        bookings.setBookingTime(LocalDateTime.now());

        List<BookingSeat> bSeats=new ArrayList<>();

        for(ShowSeat ss:showSeats){

            BookingSeat seat1=new BookingSeat();
            seat1.setBookings(bookings);
            seat1.setPrice(ss.getPrice());
            seat1.setShowSeat(ss);

            bSeats.add(seat1);

        }

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(bookings));

        when(bookingSeatRepository.findAllByBookings(bookings)).thenReturn(bSeats);

        ResponseEntity<String> response=bookingService.cancelBooking(bookingId);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Your booking is cancelled");

        ArgumentCaptor<Bookings> bookingCaptor=ArgumentCaptor.forClass(Bookings.class);
        verify(bookingRepository).save(bookingCaptor.capture());

        Bookings bookings2=bookingCaptor.getValue();

        assertThat(bookings2.getBookingStatus()).isEqualTo(BookingStatus.CANCELLED);


        ArgumentCaptor<List<ShowSeat>> showSeatCaptor=ArgumentCaptor.forClass(List.class);
        verify(showSeatsRepository).saveAll(showSeatCaptor.capture());

        List<ShowSeat> showSeats2=showSeatCaptor.getValue();
        assertThat(showSeats2).hasSize(3);
        
        for(ShowSeat seat: showSeats2){
            assertThat(seat.getSeatStatus()).isEqualTo(ShowSeatStatus.AVAILABLE);
            assertThat(seat.getHeldUntil()).isNull();
        }


    }

    @Test 
    public void testConcurrrencyForHoldSeats(){

        UserContext userContext=new UserContext();
        userContext.setUserId(1l);

        Authentication authentication=mock(Authentication.class);

        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        
        Long showId=1l;
        List<Long> seats=List.of(1l);

        Shows show=new Shows();
        show.setId(showId);

        when(showsRepository.findById(showId)).thenReturn(Optional.of(show));

        List<ShowSeat> showSeats=new ArrayList<>();
        for(Long id:seats){
            ShowSeat showSeat=new ShowSeat();
            showSeat.setId(id);
            showSeat.setShows(show);
            showSeat.setSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeat.setPrice(100.0);
            Seats seat = new Seats();
            seat.setSeatNumber("A" + id);

            showSeat.setSeats(seat);
            showSeats.add(showSeat);
        }

        when(showSeatsRepository.findAllById(seats)).thenReturn(showSeats);
        for(ShowSeat ss: showSeats){
        when(showSeatsRepository.existsByIdAndShowsAndSeatStatus(ss.getId(), show, ShowSeatStatus.AVAILABLE)).thenReturn(true,false);
        }

        when(bookingRepository.save(any(Bookings.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

        SeatHoldRequestDTO request1=new SeatHoldRequestDTO();
        request1.setShowId(showId);
        request1.setSeats(seats);

        ResponseEntity<SeatHoldResponseDTO> response =bookingService.holdSeat(request1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        for(ShowSeat ss:showSeats){
            assertThat(ss.getSeatStatus()).isEqualTo(ShowSeatStatus.HELD);
        }


        

        UserContext userContext1=new UserContext();
        userContext1.setUserId(2l);

        Authentication authentication1=mock(Authentication.class);

        when(authentication1.getDetails()).thenReturn(userContext1);

        SecurityContext securityContext1=SecurityContextHolder.createEmptyContext();
        securityContext1.setAuthentication(authentication1);
        SecurityContextHolder.setContext(securityContext1);

        SeatHoldRequestDTO request2=new SeatHoldRequestDTO();
        request2.setShowId(showId);
        request2.setSeats(seats);

        assertThatThrownBy(()->bookingService.holdSeat(request2)).isInstanceOf(ResourceNotFoundException.class);










    }


}
