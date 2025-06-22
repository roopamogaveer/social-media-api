package com.roopa.social_media.authentication.service;

import com.roopa.social_media.authentication.dto.Message;
import com.roopa.social_media.authentication.model.User;
import com.roopa.social_media.authentication.repository.UserRepository;
import com.roopa.social_media.core.enums.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger= LoggerFactory.getLogger(AuthService.class);


    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);

    }

    public Message addUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new Message(MessageStatus.SUCCESS,"User registered successfully");
    }

    public List<User> allUser()
    {
        return userRepository.findAll();
    }

    public Optional<User> findUser(int id)
    {
        return userRepository.findById(id);
    }

    public Boolean deleteUser(int id)
    {
        try
        {
            logger.info("Checking if user exists");
            if(userRepository.findById(id).isPresent())
            {
                logger.info("Deleting user");
                userRepository.deleteById(id);
                return true;
            }
            logger.warn("User doesn't exist");
            return false;
        }
        catch(Exception exception)
        {
            logger.error("Exception occurred : {} ",exception.getMessage());
            return false;
        }

    }

    public String getLoggedInUser()
    {
        logger.info("Fetching current logged-in User");
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}