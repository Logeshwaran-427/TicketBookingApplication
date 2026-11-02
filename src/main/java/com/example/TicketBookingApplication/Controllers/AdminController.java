package com.example.TicketBookingApplication.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketBookingApplication.DTOs.CommentDTO;
import com.example.TicketBookingApplication.DTOs.MovieRequestDTO;
import com.example.TicketBookingApplication.Service.AuthService;
import com.example.TicketBookingApplication.Service.MovieService;



@RestController 
@RequestMapping("/admin")
public class AdminController {

    private final AuthService authService;
    private final MovieService movieService;

    public AdminController(MovieService movieService,AuthService authService ){
        this.movieService=movieService;
        this.authService = authService;
    }

    // {
    //     "title":"Dude",
    //     "description":"Love flim",
    //     "duration":129,
    //     "category":"COMEDY",
    //     "posterUrl":"http://google.com",
    //     "language":"Tamil",
    //     "censorRating":"U",
    //     "releaseDate":"2026-10-02"
    
    
    // }

    @PreAuthorize ("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<String> postMethodName(@RequestBody MovieRequestDTO movieRequestDTO) {
        return movieService.addMovie(movieRequestDTO);
    }

    @PreAuthorize ("hasRole('ADMIN')")
    @PatchMapping("/theatreOwner/approve/{id}")
    public ResponseEntity<String> approveRequests(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return authService.approveTheatreOwnerRequests(id,commentDTO);
    }

    @PreAuthorize ("hasRole('ADMIN')")
    @PatchMapping("/theatreOwner/reject/{id}")
    public ResponseEntity<String> rejectRequests(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return authService.rejectTheatreOwnerRequests(id, commentDTO);
    }
    
    
    
}
