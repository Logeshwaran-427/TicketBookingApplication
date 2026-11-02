package com.example.TicketBookingApplication;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.TicketBookingApplication.Entities.Movie;
import com.example.TicketBookingApplication.Entities.User;
import com.example.TicketBookingApplication.Entities.Venue;
import com.example.TicketBookingApplication.Enums.MovieCategory;
import com.example.TicketBookingApplication.Enums.Role;
import com.example.TicketBookingApplication.Repository.MovieRepository;
import com.example.TicketBookingApplication.Repository.TheatreOwnerRequestRepo;
import com.example.TicketBookingApplication.Repository.UserRepository;
import com.example.TicketBookingApplication.Repository.VenueRepository;

@SpringBootApplication
@EnableScheduling 
public class TicketBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingApplication.class, args);
	}

	// @Autowired 
	// UserRepository userRepository;

	// @Autowired 
	// PasswordEncoder passwordEncoder;

	// @Autowired 
	// MovieRepository movieRepository;

	// @Autowired 
	// TheatreOwnerRequestRepo theatreOwnerRequestRepo;

	// @Autowired 
	// VenueRepository venueRepository;


	// @Bean 
	// public CommandLineRunner runner(){
	// 	return  args->{
	// 		if(userRepository.count()==0){
	// 			User user=new User();
	// 			user.setName("Loki");
	// 			user.setPassword(passwordEncoder.encode("Logesh@427"));
	// 			user.setEmail("logesh@gmail.com");
	// 			user.setRole(Role.ADMIN);
	// 			userRepository.save(user);	

	// 			User user1=new User();
	// 			user1.setName("Aswin");
	// 			user1.setPassword(passwordEncoder.encode("Aswin@427"));
	// 			user1.setEmail("Aswin@gmail.com");
	// 			user1.setRole(Role.THEATRE_OWNER);
	// 			User theatreOwner=userRepository.save(user1);

	// 			Venue venue=new Venue();
	// 			venue.setLocation("Erode");
	// 			venue.setName("Vel");
	// 			venue.setOwnerId(theatreOwner.getId());
	// 			venueRepository.save(venue);


	// 			User user2=new User();
	// 			user2.setName("Abhi");
	// 			user2.setPassword(passwordEncoder.encode("Abhi@427"));
	// 			user2.setEmail("Abhi@gmail.com");
	// 			user2.setRole(Role.MEMBER);
	// 			userRepository.save(user2);

				
	// 		}

	// 		if(movieRepository.count()==0){
	// 			Movie movie=new Movie();
	// 			movie.setCategory(MovieCategory.ACTION);
	// 			movie.setCensorRating("A");
	// 			movie.setDescription("Action Drama");
	// 			movie.setDuration(150);
	// 			movie.setLanguage("Tamil");
	// 			movie.setPosterUrl("www.google.com");
	// 			movie.setReleaseDate(LocalDate.of(2026, 10, 15));
	// 			movie.setTitle("Jailer 2");

	// 			movieRepository.save(movie);
	// 		}

	// 	};
	// }

}
