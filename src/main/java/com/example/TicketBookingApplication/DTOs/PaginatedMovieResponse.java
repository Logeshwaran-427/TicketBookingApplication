package com.example.TicketBookingApplication.DTOs;

import java.util.List;

public class PaginatedMovieResponse {

    List<MovieDetailsResponseDTO> movieDetailsResponseDTOs;
    int pageNumber;
    int totalPages;

    public PaginatedMovieResponse(List<MovieDetailsResponseDTO> movieDetailsResponseDTOs, int pageNumber,
            int totalPages) {
        this.movieDetailsResponseDTOs = movieDetailsResponseDTOs;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
    }

    public PaginatedMovieResponse() {
    }
    
    public List<MovieDetailsResponseDTO> getMovieDetailsResponseDTOs() {
        return movieDetailsResponseDTOs;
    }
    public void setMovieDetailsResponseDTOs(List<MovieDetailsResponseDTO> movieDetailsResponseDTOs) {
        this.movieDetailsResponseDTOs = movieDetailsResponseDTOs;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    
    
}
