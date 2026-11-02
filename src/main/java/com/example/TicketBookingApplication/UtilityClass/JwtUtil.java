package com.example.TicketBookingApplication.UtilityClass;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.TicketBookingApplication.Entities.User;
import com.example.TicketBookingApplication.Exceptions.ResourceNotFoundException;
import com.example.TicketBookingApplication.Repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component 
public class JwtUtil {

    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    @Value ("${app.secretKey}")
    String secretKey;

    Key key;

    @PostConstruct 
    public void init(){
    key=Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    long expiryTime=1000*60*60;


    public String generateToken(String name){

        User user=userRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("User not found"));
        Map<String,String> roleClaim=new HashMap<>();
        roleClaim.put("role", user.getRole().name());

        return Jwts.builder().
        setClaims(roleClaim).
        claim("userId", user.getId()).
        setSubject(name).
        setIssuedAt(new Date()).
        setExpiration(new Date(System.currentTimeMillis()+expiryTime)).
        signWith(key,SignatureAlgorithm.HS256).compact();

    }

    public Claims extractBody(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String extractSubject(String token){
        return extractBody(token).getSubject();
    }

    public boolean checkValidToken(String token,UserDetails userDetails){
        return !checkPasswordExpired(token) && extractSubject(token).equals(userDetails.getUsername());
    }

    public boolean checkPasswordExpired(String token){
        return extractBody(token).getExpiration().before(new Date());
    }

    public Long getUserId(String token){
        return  extractBody(token).get("userId", Long.class);
    }

    public String getRole(String token){
        return  extractBody(token).get("role",String.class);
    }
    
}
