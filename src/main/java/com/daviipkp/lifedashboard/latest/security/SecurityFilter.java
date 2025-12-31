package com.daviipkp.lifedashboard.latest.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.daviipkp.lifedashboard.latest.instance.ResponseType;
import com.daviipkp.lifedashboard.latest.instance.ServerResponse;
import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import com.daviipkp.lifedashboard.latest.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRep userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if(token != null){
            String login = null;
            try{
                login = tokenService.validateToken(token);
            }catch (JWTVerificationException exception){
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            if(login != null && !login.isEmpty()){
                UserDetails user = userRepository.findByUsername(login).orElse(null);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}