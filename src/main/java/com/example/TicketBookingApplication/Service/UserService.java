package com.example.TicketBookingApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TicketBookingApplication.DTOs.RegisterRequestDTO;
import com.example.TicketBookingApplication.Entities.User;
import com.example.TicketBookingApplication.Enums.Role;
import com.example.TicketBookingApplication.Exceptions.BadRequestException;
import com.example.TicketBookingApplication.Repository.UserRepository;

@Service 
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService (UserRepository userRepository){
        this.userRepository=userRepository;
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
    }

    
    
}
