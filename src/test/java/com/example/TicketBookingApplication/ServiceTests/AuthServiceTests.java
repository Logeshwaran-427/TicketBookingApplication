package com.example.TicketBookingApplication.ServiceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.security.crypto.password.PasswordEncoder;

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
import com.example.TicketBookingApplication.Service.AuthService;

@ExtendWith (MockitoExtension.class)
public class AuthServiceTests {
    
    @Mock 
    UserRepository userRepository;

    @Mock 
    PasswordEncoder passwordEncoder;

    @Mock 
    TheatreOwnerRequestRepo theatreOwnerRequestRepo;

    @Mock 
    VenueRepository venueRepository;

    @InjectMocks 
    AuthService authService;

    @Test 
    public void testRegisterMethod(){

        RegisterRequestDTO user=new RegisterRequestDTO(null, null, null);

        
        user.setEmail("Loki@gmail.com");
        user.setName("Logesh");
        user.setPassword("Logesh@427");
        

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        when(passwordEncoder.encode("Logesh@427")).thenReturn("encodedPassword");

        ResponseEntity<String> expected=authService.registerMember(user);

        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(expected.getBody()).isEqualTo("Registeration Successful!");

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("Logesh@427");

    }


    @Test
    public void testRegisterMethod2(){
        RegisterRequestDTO user=new RegisterRequestDTO(null, null, null);

        user.setEmail("Loki@gmail.com");
        user.setName("Logesh");
        user.setPassword("Logesh@427");
        

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThatThrownBy(()->authService.registerMember(user)).isInstanceOf(BadRequestException.class).hasMessage("Given email already registered");

        verify(userRepository,never()).save(any(User.class));
        verify(passwordEncoder,never()).encode("Logesh@427");

    }

    @Test 
    public void testApplyforTheatreOwnerMethod(){
        TheatreOwnerDTO ownerDTO=new TheatreOwnerDTO();
        ownerDTO.setLocation("Erode");
        ownerDTO.setTheaterName("Vel");

        Long userId=2l;

        UserContext userContext=new UserContext();
        userContext.setUserId(userId);

        Authentication authentication=mock(Authentication.class);
        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId,RequestStatus.PENDING )).thenReturn(false);
        when(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId, RequestStatus.APPROVED)).thenReturn(false);

        ResponseEntity<String> response=authService.applyForTheatreOwner(ownerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Your Request for theatre owner is pending for approval");

        // verify(theatreOwnerRequestRepo).save(any(TheatreOwnerRequest.class));

        ArgumentCaptor<TheatreOwnerRequest> argumentCaptor=ArgumentCaptor.forClass(TheatreOwnerRequest.class);
        
        verify(theatreOwnerRequestRepo).save(argumentCaptor.capture());

        TheatreOwnerRequest savedRequest=argumentCaptor.getValue();

        assertThat(savedRequest.getUserId()).isEqualTo(2l);
        assertThat(savedRequest.getLocation()).isEqualTo("Erode");
        assertThat(savedRequest.getTheaterName()).isEqualTo("Vel");
        assertThat(savedRequest.getStatus()).isEqualTo(RequestStatus.PENDING);
        
    }

    @Test
    public void testApplyforTheatreOwnerMethod2(){
        TheatreOwnerDTO ownerDTO=new TheatreOwnerDTO();
        ownerDTO.setLocation("Erode");
        ownerDTO.setTheaterName("Vel");

        Long userId=2l;

        UserContext userContext=new UserContext();
        userContext.setUserId(userId);

        Authentication authentication=mock(Authentication.class);
        
        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId, RequestStatus.PENDING)).thenReturn(false);

        when(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId, RequestStatus.APPROVED)).thenReturn(true);
        
        assertThatThrownBy(()->authService.applyForTheatreOwner(ownerDTO)).isInstanceOf(BadRequestException.class).hasMessage("You are already an Theatre Ownwer");

        verify(theatreOwnerRequestRepo,never()).save(any(TheatreOwnerRequest.class));
    }

    @Test 
    public void testApplyforTheatreOwnerMethod3(){

        TheatreOwnerDTO ownerDTO=new TheatreOwnerDTO();
        ownerDTO.setLocation("Erode");
        ownerDTO.setTheaterName("Vel");

        Long userId=2l;

        UserContext userContext=new UserContext();
        userContext.setUserId(userId);
        Authentication authentication=mock(Authentication.class);

        when(authentication.getDetails()).thenReturn(userContext);

        SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(theatreOwnerRequestRepo.existsByUserIdAndStatus(userId, RequestStatus.PENDING)).thenReturn(true);

        assertThatThrownBy(()->authService.applyForTheatreOwner(ownerDTO)).isInstanceOf(BadRequestException.class).hasMessage("User have already applied");

        verify(theatreOwnerRequestRepo,never()).save(any(TheatreOwnerRequest.class));

    }


    @Test 
    public void testApproveTheatreOwnerRequest(){

        Long id = 1L;

        TheatreOwnerRequest request = new TheatreOwnerRequest();
        request.setId(id);
        request.setUserId(10L);
        request.setTheaterName("VEL");
        request.setLocation("Erode");
        request.setStatus(RequestStatus.PENDING);

        CommentDTO comment=new CommentDTO();
        comment.setComments("Approved");

        User user=new User();
        user.setId(10L);
        user.setRole(Role.MEMBER);

        when(theatreOwnerRequestRepo.findById(id)).thenReturn(Optional.of(request));

        when(userRepository.findById(request.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<String> response=authService.approveTheatreOwnerRequests(id, comment);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Theatre Owner request approved");
        
        assertThat(request.getComments()).isEqualTo("Approved");
        assertThat(request.getStatus()).isEqualTo(RequestStatus.APPROVED);
        assertThat(user.getRole()).isEqualTo(Role.THEATRE_OWNER);

        ArgumentCaptor<Venue> venueCaptor=ArgumentCaptor.forClass(Venue.class);
        verify(venueRepository).save(venueCaptor.capture());
        Venue venue=venueCaptor.getValue();
        assertThat(venue.getLocation()).isEqualTo("Erode");
        assertThat(venue.getName()).isEqualTo("VEL");
        assertThat(venue.getOwnerId()).isEqualTo(10L);


    }

    @Test 
    public void testApproveTheatreOwnerRequest2(){
        Long id = 1L;

        TheatreOwnerRequest request = new TheatreOwnerRequest();
        request.setId(id);
        request.setUserId(10L);
        request.setTheaterName("VEL");
        request.setLocation("Erode");
        request.setStatus(RequestStatus.PENDING);

        CommentDTO comment=new CommentDTO();
        comment.setComments("Approved");

        User user=new User();
        user.setId(10L);
        user.setRole(Role.MEMBER);

        when(theatreOwnerRequestRepo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()->authService.approveTheatreOwnerRequests(id, comment)).isInstanceOf(ResourceNotFoundException.class).hasMessage("Id not found");

        verify(userRepository,never()).save(any(User.class));
        verify(theatreOwnerRequestRepo,never()).save(any(TheatreOwnerRequest.class));
        verify(venueRepository,never()).save(any(Venue.class));


    }
    
}
