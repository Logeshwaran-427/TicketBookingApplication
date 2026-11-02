package com.example.TicketBookingApplication.DTOs;

import jakarta.validation.constraints.NotBlank;

public class TheatreOwnerDTO {

    
    @NotBlank (message = "Please enter the theatre name")
    String theaterName;
    @NotBlank (message = "Please enter the location")
    String location;
    
    public TheatreOwnerDTO(String theaterName, String location) {
        this.theaterName = theaterName;
        this.location = location;
    }
    public TheatreOwnerDTO() {
    }
    public String getTheaterName() {
        return theaterName;
    }
    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    
}
