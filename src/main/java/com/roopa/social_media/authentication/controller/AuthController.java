package com.roopa.social_media.authentication.controller;

import com.roopa.social_media.authentication.dto.AuthRequest;
import com.roopa.social_media.authentication.dto.Message;
import com.roopa.social_media.authentication.model.User;
import com.roopa.social_media.authentication.service.AuthService;
import com.roopa.social_media.authentication.service.JwtService;
import com.roopa.social_media.core.enums.MessageStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
- Controller class for User related requests
*/

@RestController
@RequestMapping("/api/v1/users")
public class AuthController
{
    @Autowired
    public AuthService authService;

    @Autowired
    public JwtService jwtService;

    @Autowired
    public AuthenticationManager authenticationManager;

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    @PostMapping("/create")
    public ResponseEntity<Message> addUser(@RequestBody User user)
    {
        Message response;

        try
        {
            logger.info("Creating user : {}",user.getEmail());
            response= authService.addUser(user);
        }
        catch (Exception exception)
        {
            logger.error("Error occurred while creating user : {}",exception.getMessage());
            response=new Message(MessageStatus.FAILURE,"Unable to create account");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Message> authenticate(@RequestBody AuthRequest authRequest)
    {

        Authentication authentication=null;
        try
        {
            logger.info("Authenticating user : {}",authRequest.getEmail());
            authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        }
        catch(Exception exception)
        {
            logger.error("Exception occurred while authenticating user : {}",exception.getMessage());
        }

        if(authentication!=null && authentication.isAuthenticated())
        {
            String jwt=jwtService.generateToken(authRequest.getEmail());
            Message response=new Message(MessageStatus.SUCCESS,jwt);
            logger.info("Successfully authenticated the User");
            return ResponseEntity.ok(response);
        }

        Message response=new Message(MessageStatus.FAILURE,"Invalid username or password");
        return ResponseEntity.badRequest().body(response);

    }

    @GetMapping("/")
    public ResponseEntity<List<User>> fetchAll()
    {
        logger.info("Fetching all Users");
        List<User> users=authService.allUser();
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable int id)
    {
        logger.info("Deleting user : {}",id);
        Message message;
        if(authService.deleteUser(id))
        {
            message=new Message(MessageStatus.SUCCESS,"Deleted successfully");
        }
        else
        {
            message=new Message(MessageStatus.FAILURE,"Unable to delete");
        }

        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUser(@PathVariable int id)
    {
        logger.info("Fetching User id : {}",id);
        Optional<User> user=authService.findUser(id);
        return ResponseEntity.ok(user);
    }
}