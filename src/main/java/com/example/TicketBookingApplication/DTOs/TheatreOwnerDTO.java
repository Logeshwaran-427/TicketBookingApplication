package com.example.TicketBookingApplication.DTOs;

public class TheatreOwnerDTO {

    String theaterName;
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
