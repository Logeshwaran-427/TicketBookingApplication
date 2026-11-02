package com.example.TicketBookingApplication.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketBookingApplication.DTOs.LoginDTO;
import com.example.TicketBookingApplication.DTOs.RegisterRequestDTO;
import com.example.TicketBookingApplication.DTOs.TheatreOwnerDTO;
import com.example.TicketBookingApplication.Service.AuthService;
import com.example.TicketBookingApplication.Service.UserService;
import com.example.TicketBookingApplication.UtilityClass.JwtUtil;



@RestController 
@RequestMapping ("/auth")
public class AuthController {


    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,AuthService authService, AuthenticationManager authenticationManager,JwtUtil jwtUtil){
        this.authService=authService;
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.userService=userService;
    }

    @PostMapping("/login")
    public String postMethodName(@RequestBody LoginDTO login) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getName(), login.getPassword()));
        String token=jwtUtil.generateToken(login.getName());
        return token;
    }

    @PostMapping("/register")
    public ResponseEntity<String> postMethodName(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return authService.registerMember(registerRequestDTO);
    }

    @PreAuthorize ("hasRole('MEMBER')")
    @PostMapping("/applyTheatreOwner")
    public ResponseEntity<String> applyForTheatreOwnerAccess(@RequestBody TheatreOwnerDTO theatreOwnerDTO) {
        return authService.applyForTheatreOwner(theatreOwnerDTO);
    }

    
    
    

    
    
    
    
}
