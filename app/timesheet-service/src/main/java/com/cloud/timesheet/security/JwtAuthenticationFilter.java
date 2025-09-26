package com.cloud.timesheet.security;

import com.cloud.timesheet.dto.IntrospectionResponse;
import com.cloud.timesheet.service.TokenIntrospectionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenIntrospectionService tokenIntrospectionService;

    public JwtAuthenticationFilter(TokenIntrospectionService tokenIntrospectionService) {
        this.tokenIntrospectionService = tokenIntrospectionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("JWT Filter - Request URI: " + request.getRequestURI());
        
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("JWT Filter - Auth Header: " + authHeader);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JWT Filter - No valid auth header, proceeding without authentication");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        System.out.println("JWT Filter - Extracted token: " + token.substring(0, Math.min(20, token.length())) + "...");
        
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            IntrospectionResponse introspectionResponse = 
                tokenIntrospectionService.introspectToken(token);
            
            System.out.println("JWT Filter - Introspection active: " + introspectionResponse.isActive());
            System.out.println("JWT Filter - Username: " + introspectionResponse.getUsername());
            
            if (introspectionResponse.isActive() && introspectionResponse.getUsername() != null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        introspectionResponse.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("JWT Filter - Authentication set successfully");
            } else {
                System.out.println("JWT Filter - Authentication failed");
            }
        }

        filterChain.doFilter(request, response);
    }
}


