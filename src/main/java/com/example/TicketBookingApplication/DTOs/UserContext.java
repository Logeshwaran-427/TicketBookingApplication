package com.example.TicketBookingApplication.DTOs;

public class UserContext {
    Long userId;
    String role;

    public UserContext(Long userId, String role) {
        this.userId = userId;
        this.role = role;
    }

    public UserContext() {
    }
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
