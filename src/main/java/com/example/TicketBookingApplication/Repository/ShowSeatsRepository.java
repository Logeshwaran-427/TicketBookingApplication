package com.example.TicketBookingApplication.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.ShowSeat;
import com.example.TicketBookingApplication.Entities.Shows;
import com.example.TicketBookingApplication.Enums.ShowSeatStatus;

public interface ShowSeatsRepository extends JpaRepository<ShowSeat,Long> {

    boolean existsByIdAndShowsAndSeatStatus(Long id,Shows show, ShowSeatStatus seatStatus );

    List<ShowSeat> findAllBySeatStatus(ShowSeatStatus seatStatus);

    List<ShowSeat> findAllByShows(Shows show);
    
}
