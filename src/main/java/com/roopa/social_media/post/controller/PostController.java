package com.roopa.social_media.post.controller;

import com.roopa.social_media.authentication.dto.Message;
import com.roopa.social_media.authentication.model.User;
import com.roopa.social_media.authentication.service.AuthService;
import com.roopa.social_media.core.enums.MessageStatus;
import com.roopa.social_media.post.dto.PostDto;
import com.roopa.social_media.post.model.Post;
import com.roopa.social_media.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
- Controller for Post related operations/requests
 */

@RestController
@RequestMapping("/api/v1/posts")
public class PostController
{

    @Autowired
    AuthService authService;

    @Autowired
    PostService postService;

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    @PostMapping("/")
    public ResponseEntity<Message> addPost(@RequestBody PostDto postDto)
    {
        try
        {
            logger.info("Adding post");
            User user=authService.findUserByEmail(authService.getLoggedInUser());
            Post post=new Post();
            post.content=postDto.content;
            post.postTime = LocalDateTime.now();
            post.user=user;
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.addPost(post));

        }
        catch (Exception exception)
        {
            Message response=new Message(MessageStatus.FAILURE,"Unable to post, try again");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Message> removePost(@PathVariable int id)
    {
        try
        {
            logger.info("Deleting post : {}",id);
            String email=authService.getLoggedInUser();
            Post post=postService.getPost(id);
            if(post.getUser().getEmail().equals(email))
            {
                return ResponseEntity.ok(postService.removePost(id));
            }
            else
            {
                logger.error("User not authorized to delete posts of other users");
                Message response=new Message(MessageStatus.FAILURE,"Not authorized to delete this post");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
            }

        }
        catch (Exception exception)
        {
            Message response=new Message(MessageStatus.FAILURE,"Unable to post, try again");
            return ResponseEntity.badRequest().body(response);
        }
    }



    @GetMapping("/")
    public ResponseEntity<List<Post>> fetchAll()
    {
        try
        {
            logger.info("Fetching all post");
            List<Post> posts=postService.allPosts();
            return ResponseEntity.ok(posts);
        }
        catch (Exception exception)
        {
            logger.error("Exception occurred while fetching post : {}",exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }

    }

    @PostMapping("/like/{post_id}")
    public ResponseEntity<Post> like(@PathVariable int post_id)
    {
        try
        {
            String email=authService.getLoggedInUser();
            return ResponseEntity.ok(postService.like(post_id,email));
        }
        catch (Exception exception)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


}
