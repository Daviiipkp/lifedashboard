package com.daviipkp.lifedashboard.latest.services;

import com.daviipkp.lifedashboard.latest.repositories.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRep userRepository;

    @Autowired
    UserRep repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByUsername(username).orElse(repository.findByEmail(username).orElse(null));

        if(user==null) {
            throw new UsernameNotFoundException("User " + username + " not found!");
        }

        return user;
    }
}
