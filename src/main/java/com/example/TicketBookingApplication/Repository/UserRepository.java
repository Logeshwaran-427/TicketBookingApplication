package com.example.TicketBookingApplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.TicketBookingApplication.Entities.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User,Long>{

    
    Optional<User> findByName(String name);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    

    
}
