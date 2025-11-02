package com.cloud.leave.security;


import com.cloud.leave.dto.IntrospectionResponse;
import com.cloud.leave.service.TokenIntrospectionService;
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

        // Skip JWT processing for public endpoints
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/timesheets/health")  || requestURI.startsWith("/public/")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            IntrospectionResponse introspectionResponse =
                tokenIntrospectionService.introspectToken(token);
            
            if (introspectionResponse.isActive() && introspectionResponse.getUsername() != null) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        introspectionResponse.getUsername(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}