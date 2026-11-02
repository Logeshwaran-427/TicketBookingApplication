package com.example.TicketBookingApplication.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.TicketBookingApplication.DTOs.BookingResponseDTO;
import com.example.TicketBookingApplication.DTOs.SeatHoldRequestDTO;
import com.example.TicketBookingApplication.DTOs.SeatHoldResponseDTO;
import com.example.TicketBookingApplication.DTOs.UserContext;
import com.example.TicketBookingApplication.Entities.BookingSeat;
import com.example.TicketBookingApplication.Entities.Bookings;
import com.example.TicketBookingApplication.Entities.ShowSeat;
import com.example.TicketBookingApplication.Entities.Shows;
import com.example.TicketBookingApplication.Enums.BookingStatus;
import com.example.TicketBookingApplication.Enums.ShowSeatStatus;
import com.example.TicketBookingApplication.Exceptions.AuthorizationException;
import com.example.TicketBookingApplication.Exceptions.BadRequestException;
import com.example.TicketBookingApplication.Exceptions.ResourceNotFoundException;
import com.example.TicketBookingApplication.Exceptions.SeatUnavailableException;
import com.example.TicketBookingApplication.Repository.BookingRepository;
import com.example.TicketBookingApplication.Repository.BookingSeatRepository;
import com.example.TicketBookingApplication.Repository.MovieRepository;
import com.example.TicketBookingApplication.Repository.SeatRepository;
import com.example.TicketBookingApplication.Repository.ShowSeatsRepository;
import com.example.TicketBookingApplication.Repository.ShowsRepository;
import com.example.TicketBookingApplication.Repository.VenueRepository;

import jakarta.transaction.Transactional;

@Service 
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowsRepository showsRepository;
    private final ShowSeatsRepository seatsRepository;
    private final MovieRepository movieRepository;
    private final SeatRepository seatRepository;
    private final VenueRepository venueRepository;

    public BookingService(BookingRepository bookingRepository, BookingSeatRepository bookingSeatRepository,
            ShowsRepository showsRepository, ShowSeatsRepository seatsRepository, MovieRepository movieRepository,
            SeatRepository seatRepository, VenueRepository venueRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.showsRepository = showsRepository;
        this.seatsRepository = seatsRepository;
        this.movieRepository = movieRepository;
        this.seatRepository = seatRepository;
        this.venueRepository = venueRepository;
    }

    @Transactional 
    public ResponseEntity<SeatHoldResponseDTO> holdSeat(SeatHoldRequestDTO seatHoldRequestDTO){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext=(UserContext) auth.getDetails();
        Long userId=userContext.getUserId();

        Shows show=showsRepository.findById(seatHoldRequestDTO.getShowId()).orElseThrow(()-> new ResourceNotFoundException("Show not found"));

        List<ShowSeat> seatIds=seatsRepository.findAllById(seatHoldRequestDTO.getSeats());
        Double totalPrice=0.0;

        for(ShowSeat seat:seatIds){
            if(!seatsRepository.existsByIdAndShowsAndSeatStatus(seat.getId(),show, ShowSeatStatus.AVAILABLE)){

                throw new ResourceNotFoundException("Selected seat is not available ");

            }
        }

        Bookings bookings=new Bookings();
        bookings.setUserId(userId);
        bookings.setBookingStatus(BookingStatus.PAYMENT_PENDING);
        bookings.setBookingTime(LocalDateTime.now());
        bookings.setShows(show);
        bookings=bookingRepository.save(bookings);

        List<BookingSeat> bs=new ArrayList<>();
        LocalDateTime heldExpiryTime=LocalDateTime.now().plusMinutes(5);

        for(ShowSeat seat:seatIds){
            seat.setSeatStatus(ShowSeatStatus.HELD);
            seat.setHeldUntil(heldExpiryTime);
            try{
            seatsRepository.save(seat);
            }
            catch(OptimisticEntityLockException ex){
                throw new SeatUnavailableException("Seat "+seat.getSeats().getSeatNumber() + " was just taken by someone else");
            }

            BookingSeat bookingSeat=new BookingSeat();
            bookingSeat.setBookings(bookings);
            bookingSeat.setShowSeat(seat);
            bookingSeat.setPrice(seat.getPrice());
            bs.add(bookingSeat);
        
            totalPrice=totalPrice+seat.getPrice();

        }

        bookingSeatRepository.saveAll(bs);

        bookings.setTotalAmount(totalPrice);
        bookingRepository.save(bookings);
        
        SeatHoldResponseDTO seatHoldResponseDTO=new SeatHoldResponseDTO();
        seatHoldResponseDTO.setBookingId(bookings.getId());
        seatHoldResponseDTO.setHeldExpiresAt(heldExpiryTime);
        seatHoldResponseDTO.setTotalAmount(totalPrice);
        seatHoldResponseDTO.setHeldSeats(bs.stream().map(ss->ss.getShowSeat().getSeats().getSeatNumber()).toList());

        return ResponseEntity.status(HttpStatus.CREATED).body(seatHoldResponseDTO);
    }

    @Transactional
    public ResponseEntity<BookingResponseDTO> confirmBooking(Long bookingId){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext=(UserContext) auth.getDetails();
        Long userId=userContext.getUserId();

        Bookings bookings=bookingRepository.findById(bookingId).orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        if (bookings.getBookingStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new BadRequestException("This booking cannot be confirmed (current status: " + bookings.getBookingStatus() + ")");
        }

        if(!bookings.getUserId().equals(userId)){
            throw new AuthorizationException("Please enter your booking id");
        }

        List<BookingSeat> bookingSeats=bookingSeatRepository.findAllByBookings(bookings);

        LocalDateTime curTime=LocalDateTime.now();

        for(BookingSeat bs:bookingSeats){
            if(bs.getShowSeat().getHeldUntil().isBefore(curTime)){
                throw new BadRequestException("Please select the seats. Your selected seats are expired");
            }
        }
        List<ShowSeat> showSeats=new ArrayList<>();
        for(BookingSeat bs:bookingSeats){
            ShowSeat showSeat=bs.getShowSeat();
            showSeat.setSeatStatus(ShowSeatStatus.BOOKED);
            showSeats.add(showSeat);
        }

        seatsRepository.saveAll(showSeats);

        bookings.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(bookings);

        BookingResponseDTO bookingResponseDTO=new BookingResponseDTO();
        bookingResponseDTO.setBookingId(bookingId);
        bookingResponseDTO.setBookingTime(bookings.getBookingTime());
        bookingResponseDTO.setMovieTitle(bookings.getShows().getMovie().getTitle());
        bookingResponseDTO.setSeats(showSeats.stream().map(showseat->showseat.getSeats().getSeatNumber()).toList());
        bookingResponseDTO.setShowDateTime(bookings.getShows().getShowStarTime());
        bookingResponseDTO.setStatus(bookings.getBookingStatus());
        bookingResponseDTO.setTotalAmount(bookings.getTotalAmount());

        return ResponseEntity.status(HttpStatus.OK).body(bookingResponseDTO);
        
    }

    @Transactional 
    public ResponseEntity<String> cancelBooking(Long bookingId){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext=(UserContext) auth.getDetails();
        Long userId=userContext.getUserId();


        Bookings bookings=bookingRepository.findById(bookingId).orElseThrow(()-> new ResourceNotFoundException("Booking not found"));

        if(!bookings.getUserId().equals(userId)){
            throw new AuthorizationException("You are not authorized to cancel this booking");
        } 

        if (bookings.getBookingStatus() != BookingStatus.PAYMENT_PENDING && bookings.getBookingStatus()!=BookingStatus.CONFIRMED) {
            throw new BadRequestException("This booking cannot be cancelled (current status: " + bookings.getBookingStatus() + ")");
        }

        if(bookings.getBookingStatus()==BookingStatus.CONFIRMED && bookings.getShows().getShowStarTime().isBefore(LocalDateTime.now())){
            throw new BadRequestException("A confirmed booking cannot be cancelled after the show has started");
        }

        

        List<BookingSeat> bookingSeats=bookingSeatRepository.findAllByBookings(bookings);

        List<ShowSeat> showSeats=new ArrayList<>();
        for(BookingSeat bs:bookingSeats){
            ShowSeat showSeat=bs.getShowSeat();
            showSeat.setSeatStatus(ShowSeatStatus.AVAILABLE);
            showSeat.setHeldUntil(null);
            showSeats.add(showSeat);
        }

        seatsRepository.saveAll(showSeats);

        bookings.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(bookings);

        return ResponseEntity.status(HttpStatus.OK).body("Your booking is cancelled");


    }




    @Scheduled (fixedRate = 50000)
    public void changeHoldedSeats(){

        List<ShowSeat> showSeats=seatsRepository.findAllBySeatStatus(ShowSeatStatus.HELD);

        if(showSeats.isEmpty()){
            return ;
        }

        List<BookingSeat> bookingSeats=bookingSeatRepository.findAllByShowSeatIn(showSeats);

        Set<Bookings> bookings=new HashSet<>();

        for(BookingSeat bs:bookingSeats){
            if(bs.getShowSeat().getHeldUntil().isBefore(LocalDateTime.now())){
                Bookings bookings1=bs.getBookings();
                if(bookings1.getBookingStatus()==BookingStatus.PAYMENT_PENDING){
                    bookings1.setBookingStatus(BookingStatus.EXPIRED);
                    bookings.add(bookings1);
                }
            }
        }
        

        for(ShowSeat seat:showSeats){
            if(seat.getHeldUntil().isBefore(LocalDateTime.now())){
                seat.setSeatStatus(ShowSeatStatus.AVAILABLE);
                seat.setHeldUntil(null);    
            }
        }

        seatsRepository.saveAll(showSeats);
        bookingRepository.saveAll(bookings);

    }


    
    
}





// @Scheduled(fixedRate = 50000)
// @Transactional
// public void changeHoldedSeats() {

//     List<ShowSeat> expiredSeats = seatsRepository
//         .findAllBySeatStatusAndHeldUntilBefore(ShowSeatStatus.HELD, LocalDateTime.now());

//     if (expiredSeats.isEmpty()) {
//         return;
//     }

//     Set<Bookings> bookingsToExpire = new HashSet<>();
//     for (BookingSeat bs : bookingSeatRepository.findAllByShowSeatIn(expiredSeats)) {
//         Bookings booking = bs.getBookings();
//         if (booking.getBookingStatus() == BookingStatus.PAYMENT_PENDING) {
//             booking.setBookingStatus(BookingStatus.EXPIRED);
//             bookingsToExpire.add(booking);
//         }
//     }

//     for (ShowSeat seat : expiredSeats) {
//         seat.setSeatStatus(ShowSeatStatus.AVAILABLE);
//         seat.setHeldUntil(null);
//     }

//     seatsRepository.saveAll(expiredSeats);
//     bookingRepository.saveAll(bookingsToExpire);
// }