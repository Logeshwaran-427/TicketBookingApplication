package com.example.TicketBookingApplication;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Entities.Seats;
import com.example.TicketBookingApplication.Entities.Venue;
import com.example.TicketBookingApplication.Enums.MovieCategory;
import com.example.TicketBookingApplication.Enums.SeatType;
import com.example.TicketBookingApplication.Repository.MovieRepository;
import com.example.TicketBookingApplication.Repository.SeatRepository;
import com.example.TicketBookingApplication.Repository.VenueRepository;



@DataJpaTest 
public class RepositoryTests {

    @Autowired 
    SeatRepository seatRepository;

    @Autowired 
    VenueRepository venueRepository;

    @Autowired
    MovieRepository movieRepository;


    @Test 
    public void shouldSeatsFindByVenue(){

        Venue venue1=new Venue();
        venue1.setLocation("Erode");
        venue1.setOwnerId(2l);
        venue1.setName("Vel");
        venueRepository.save(venue1);

        Venue venue2=new Venue();
        venue2.setLocation("Kovai");
        venue2.setOwnerId(3l);
        venue2.setName("KG");
        venueRepository.save(venue2);
        

        Seats seat1=new Seats();
        seat1.setSeatNumber("A15");
        seat1.setSeatType(SeatType.PREMIUM);
        seat1.setVenue(venue1);
        
        seatRepository.save(seat1);

        Seats seat2=new Seats();
        seat2.setSeatNumber("A16");
        seat2.setSeatType(SeatType.REGULAR);
        seat2.setVenue(venue1);
        
        seatRepository.save(seat2);

        Seats seat3=new Seats();
        seat3.setSeatNumber("A17");
        seat3.setSeatType(SeatType.PREMIUM);
        seat3.setVenue(venue2);
        
        seatRepository.save(seat3);

        List<Seats> seat=seatRepository.findByVenue(venue1);

        // assertEquals(2,seat.size());

        // assertNotNull(seat);

        // assertTrue(seat.stream().allMatch(s->s.getVenue().equals(venue1)));

        assertThat(seat).isNotNull().hasSize(2).allMatch(s->s.getVenue().equals(venue1));
    
    }


    @Test 
    public void testFindAllMovie(){

        Movie movie1=new Movie();
        movie1.setCategory(MovieCategory.ACTION);
        movie1.setCensorRating("A");
        movie1.setDescription("Action flim");
        movie1.setDuration(230);
        movie1.setLanguage("English");
        movie1.setPosterUrl("gvuhbio");
        movie1.setReleaseDate(LocalDate.of(2026, 12, 12));
        movie1.setTitle("Harry");

        movieRepository.save(movie1);

        Movie movie2=new Movie();
        movie2.setCategory(MovieCategory.COMEDY);
        movie2.setCensorRating("U");
        movie2.setDescription("Comedy flim");
        movie2.setDuration(130);
        movie2.setLanguage("English");
        movie2.setPosterUrl("gvuhbiohgcyfx");
        movie2.setReleaseDate(LocalDate.of(2026, 12, 10));
        movie2.setTitle("Porter");

        movieRepository.save(movie2);

        Pageable pageable=PageRequest.of(0,2);

        Page<Movie> movies=movieRepository.findAll(pageable);

        assertThat(movies).isNotEmpty();
        assertThat(movies.getContent()).hasSize(2);
        assertThat(movies.getNumber()).isEqualTo(0);
        assertThat(movies.getNumberOfElements()).isEqualTo(2);
        assertThat(movies.getSize()).isEqualTo(2);
        assertThat(movies.getTotalPages()).isEqualTo(1);



    }

}
