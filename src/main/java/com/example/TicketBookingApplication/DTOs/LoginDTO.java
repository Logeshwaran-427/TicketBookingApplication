package com.example.TicketBookingApplication.DTOs;

public class LoginDTO {
    
    String name;
    String password;

    public LoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public LoginDTO() {
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
