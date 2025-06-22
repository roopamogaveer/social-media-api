package com.roopa.social_media.post.controller;

import com.roopa.social_media.authentication.dto.Message;
import com.roopa.social_media.authentication.model.User;
import com.roopa.social_media.authentication.service.AuthService;
import com.roopa.social_media.core.enums.MessageStatus;
import com.roopa.social_media.post.dto.CommentDto;
import com.roopa.social_media.post.model.Comment;
import com.roopa.social_media.post.model.Post;
import com.roopa.social_media.post.service.CommentService;
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
import java.util.Optional;

/*
- Controller for Comment related operations/requests
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController
{
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    @PostMapping("/")
    public ResponseEntity<Message> addComment(@RequestBody CommentDto commentDto)
    {
        logger.info("Adding comment");

        try
        {
            User user=authService.findUserByEmail(authService.getLoggedInUser());
            Post post=postService.getPost(commentDto.post_id);

            Comment comment=new Comment();
            comment.setCommentTime(LocalDateTime.now());
            comment.setCommentText(commentDto.comment_text);
            comment.setUser(user);
            comment.setPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(comment));

        }
        catch (Exception exception)
        {
            Message response=new Message(MessageStatus.FAILURE,"Unable to comment, try again");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable int id)
    {
        try
        {
            logger.info("Fetching comments of post : {}",id);
            Post post=postService.getPost(id);
            return ResponseEntity.of(Optional.ofNullable(commentService.getAllComments(post)));
        }
        catch (Exception exception)
        {
            logger.error("Exception occurred while fetching comments - {}",exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Message> removeComment(@PathVariable int id)
    {
        try
        {
            logger.info("Deleting comment : {}",id);
            String email=authService.getLoggedInUser();
            Comment comment=commentService.getComment(id);
            if(comment.getUser().getEmail().equals(email))
            {
                return ResponseEntity.ok(commentService.removeComment(id));
            }
            else
            {
                logger.error("User not authorized to delete comments of other users");
                Message response=new Message(MessageStatus.FAILURE,"Not authorized to delete this comment");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
            }
        }
        catch (Exception exception)
        {
            Message response=new Message(MessageStatus.FAILURE,"Unable to delete comment, try again");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
