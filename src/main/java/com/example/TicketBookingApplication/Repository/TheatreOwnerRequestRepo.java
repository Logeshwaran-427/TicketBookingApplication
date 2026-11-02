package com.example.TicketBookingApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketBookingApplication.Entities.TheatreOwnerRequest;
import com.example.TicketBookingApplication.Enums.RequestStatus;

public interface TheatreOwnerRequestRepo extends JpaRepository<TheatreOwnerRequest,Long> {

    boolean existsByUserIdAndStatus(Long userId, RequestStatus status);

    boolean existsByUserId(Long userId);

    Optional<TheatreOwnerRequest> findByTheaterName(String theatreName);
    
}
