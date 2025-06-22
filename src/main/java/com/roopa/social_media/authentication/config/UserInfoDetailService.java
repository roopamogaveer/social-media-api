package com.roopa.social_media.authentication.config;

import com.roopa.social_media.authentication.model.User;
import com.roopa.social_media.authentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
- User service class which implements UserDetailsService of spring security to load the user by email for authentication and authorization
*/

@Component
public class UserInfoDetailService implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user=Optional.of(userRepository.findByEmail(email));
        return user.map(UserInfoDetails::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
    }
}