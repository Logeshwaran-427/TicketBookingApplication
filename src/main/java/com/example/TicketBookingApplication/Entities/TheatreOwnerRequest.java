package com.example.TicketBookingApplication.Entities;

import java.time.LocalDateTime;

import com.example.TicketBookingApplication.Enums.RequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity 
public class TheatreOwnerRequest {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long userId;
    String theaterName;
    String location;

    @Enumerated(EnumType.STRING)
    RequestStatus status;

    LocalDateTime appliedAt;
    LocalDateTime reviewedAt;
    String comments;
    public TheatreOwnerRequest(Long id, Long userId, String theaterName, String location, RequestStatus status,
            LocalDateTime appliedAt, LocalDateTime reviewedAt, String comments) {
        this.id = id;
        this.userId = userId;
        this.theaterName = theaterName;
        this.location = location;
        this.status = status;
        this.appliedAt = appliedAt;
        this.reviewedAt = reviewedAt;
        this.comments = comments;
    }
    public TheatreOwnerRequest() {
    }

    @PrePersist 
    public void createdDate(){
        LocalDateTime dateTime=LocalDateTime.now();
        this.appliedAt=dateTime;
        this.reviewedAt=dateTime;
    }

    @PreUpdate 
    public void updateDate(){
        this.reviewedAt=LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
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
    public RequestStatus getStatus() {
        return status;
    }
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }
    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    
    
}
