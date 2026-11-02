package com.example.TicketBookingApplication.Filters;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.TicketBookingApplication.DTOs.UserContext;
import com.example.TicketBookingApplication.Exceptions.AuthorizationException;
import com.example.TicketBookingApplication.Service.UserService;
import com.example.TicketBookingApplication.UtilityClass.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtFilter (JwtUtil jwtUtil, UserService userService){
        this.jwtUtil=jwtUtil;
        this.userService=userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            String path=request.getRequestURI();


            List<String> urls=List.of("/auth/login","/h2-console/**","/favicon.ico","/auth/register","/movies/search","/movies/category","/movies/{id}","/shows/{showID}","/movies");
            AntPathMatcher matcher = new AntPathMatcher();

            boolean isPublicUrl=urls.stream().anyMatch(url->matcher.match(url, path));
            if(isPublicUrl){
                filterChain.doFilter(request, response);
                return;
            }
        
            String header=request.getHeader("Authorization");
            if(header==null){
                throw new AuthorizationException("Header is null. Please fill header with token");
            }
            if(!header.startsWith("Bearer ")){
                throw new AuthorizationException("Invalid Bearer token");
            }
            
            String token=header.substring(7);
            String name=jwtUtil.extractSubject(token);
            
            if(name !=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails ud=userService.loadUserByUsername(name);
                if(jwtUtil.checkValidToken(token, ud)){

                    Long userId=jwtUtil.getUserId(token);
                    String role=jwtUtil.getRole(token);
                    UserContext userContext=new UserContext();
                    userContext.setRole(role);
                    userContext.setUserId(userId);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(name,null, ud.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(userContext);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
    }




    
}
