package com.example.TicketBookingApplication.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TicketBookingApplication.DTOs.CommentDTO;
import com.example.TicketBookingApplication.DTOs.RegisterRequestDTO;
import com.example.TicketBookingApplication.DTOs.TheatreOwnerDTO;
import com.example.TicketBookingApplication.DTOs.UserContext;
import com.example.TicketBookingApplication.Entities.TheatreOwnerRequest;
import com.example.TicketBookingApplication.Entities.User;
import com.example.TicketBookingApplication.Entities.Venue;
import com.example.TicketBookingApplication.Enums.RequestStatus;
import com.example.TicketBookingApplication.Enums.Role;
import com.example.TicketBookingApplication.Exceptions.BadRequestException;
import com.example.TicketBookingApplication.Exceptions.ResourceNotFoundException;
import com.example.TicketBookingApplication.Repository.TheatreOwnerRequestRepo;
import com.example.TicketBookingApplication.Repository.UserRepository;
import com.example.TicketBookingApplication.Repository.VenueRepository;

@Service 
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TheatreOwnerRequestRepo theatreOwnerRequestRepo;
    private final VenueRepository venueRepository;

    public AuthService (UserRepository userRepository, PasswordEncoder passwordEncoder, TheatreOwnerRequestRepo theatreOwnerRequestRepo,VenueRepository venueRepository){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.theatreOwnerRequestRepo=theatreOwnerRequestRepo;
        this.venueRepository=venueRepository;
    }

    public ResponseEntity<String> registerMember(RegisterRequestDTO registerRequestDTO){

        if (userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new BadRequestException("Given email already registered");
        }

        User user=new User();
        user.setName(registerRequestDTO.getName());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setEmail(registerRequestDTO.getEmail());
        user.setRole(Role.MEMBER);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Registeration Successful!");

    }

    public ResponseEntity<String> applyForTheatreOwner(TheatreOwnerDTO theatreOwnerDTO){

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        UserContext userContext=(UserContext)auth.getDetails();
        Long userId=userContext.getUserId();

        if(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId,RequestStatus.PENDING)){
            throw new BadRequestException("User have already applied");
        }

        if(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId, RequestStatus.APPROVED)){
            throw new BadRequestException("You are already an Theatre Ownwer");
        }

        TheatreOwnerRequest theatreOwnerRequest=new TheatreOwnerRequest();
        theatreOwnerRequest.setTheaterName(theatreOwnerDTO.getTheaterName());
        theatreOwnerRequest.setLocation(theatreOwnerDTO.getLocation());
        theatreOwnerRequest.setUserId(userId);
        theatreOwnerRequest.setStatus(RequestStatus.PENDING);

        theatreOwnerRequestRepo.save(theatreOwnerRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Your Request for theatre owner is pending for approval");
    }


    public ResponseEntity<String> approveTheatreOwnerRequests(Long id, CommentDTO comment){

        TheatreOwnerRequest theatreOwnerRequest=theatreOwnerRequestRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found"));

        if(theatreOwnerRequest.getStatus()!=RequestStatus.PENDING){
            throw new BadRequestException("Request has been already Reviewed");
        }

        theatreOwnerRequest.setStatus(RequestStatus.APPROVED);
        theatreOwnerRequest.setComments(comment.getComments());
        theatreOwnerRequestRepo.save(theatreOwnerRequest);

        User user=userRepository.findById(theatreOwnerRequest.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setRole(Role.THEATRE_OWNER);
        userRepository.save(user);

        Venue venue=new Venue();
        venue.setLocation(theatreOwnerRequest.getLocation());
        venue.setName(theatreOwnerRequest.getTheaterName());
        venue.setOwnerId(theatreOwnerRequest.getUserId());
        venueRepository.save(venue);

       return ResponseEntity.status(HttpStatus.OK).body("Theatre Owner request approved"); 
    

    }

    

    public ResponseEntity<String> rejectTheatreOwnerRequests(Long id, CommentDTO comment){

        TheatreOwnerRequest theatreOwnerRequest=theatreOwnerRequestRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found"));

        if(theatreOwnerRequest.getStatus()!=RequestStatus.PENDING){
            throw new BadRequestException("Request has been already Reviewed");
        }

        theatreOwnerRequest.setStatus(RequestStatus.REJECTED);
        theatreOwnerRequest.setComments(comment.getComments());
        theatreOwnerRequestRepo.save(theatreOwnerRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Request for theatre owner has been rejected");

    }
    
}
