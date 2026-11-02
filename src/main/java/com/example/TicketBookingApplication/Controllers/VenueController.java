package com.example.TicketBookingApplication.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketBookingApplication.DTOs.SeatLayoutRequestDTO;
import com.example.TicketBookingApplication.DTOs.SeatResponseDTO;
import com.example.TicketBookingApplication.DTOs.ShowRequestDTO;
import com.example.TicketBookingApplication.Service.VenueService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping ("/venue")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService){
        this.venueService=venueService;
    }

    @PreAuthorize ("hasRole('THEATRE_OWNER')")
    @PostMapping("/{id}/seats")
    public ResponseEntity<List<SeatResponseDTO>> addSeat(@PathVariable Long id, @RequestBody SeatLayoutRequestDTO seatLayoutRequestDTO) {
        return venueService.seatCreation(id, seatLayoutRequestDTO);
    }

    @PreAuthorize ("hasRole('THEATRE_OWNER')")
    @PostMapping("/shows")
    public ResponseEntity<String> postMethodName(@RequestBody ShowRequestDTO showRequestDTO) {
        return venueService.addShow(showRequestDTO);
    }
    
    
    
}




// {
//     "rows": [
//       { "rowLabel": "A", "seatCount": 20, "seatType": "PREMIUM" },
//       { "rowLabel": "B", "seatCount": 20, "seatType": "PREMIUM" },
//       { "rowLabel": "C", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "D", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "E", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "F", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "G", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "H", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "I", "seatCount": 20, "seatType": "REGULAR" },
//       { "rowLabel": "J", "seatCount": 20, "seatType": "REGULAR" }
//     ]
//   }
