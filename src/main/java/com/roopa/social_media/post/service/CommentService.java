package com.roopa.social_media.post.service;

import com.roopa.social_media.authentication.dto.Message;
import com.roopa.social_media.authentication.service.AuthService;
import com.roopa.social_media.core.enums.MessageStatus;
import com.roopa.social_media.post.dto.CommentDto;
import com.roopa.social_media.post.model.Comment;
import com.roopa.social_media.post.model.Post;
import com.roopa.social_media.post.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService
{
    @Autowired
    private CommentRepository commentRepository;

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    public Message addComment(Comment comment)
    {
        try
        {
            logger.info("Commenting on post : {}",comment.getPost().getId());
            commentRepository.save(comment);
            return new Message(MessageStatus.SUCCESS,"Commented");
        }
        catch (Exception exception)
        {
            logger.error("Exception occurred while commenting on post : {} - {}",comment.getPost().getId(),exception.getMessage());
            return new Message(MessageStatus.FAILURE,"Unable to comment");
        }
    }

    public Comment getComment(int id)
    {
        return commentRepository.findById(id).orElseThrow(()-> new RuntimeException("Unable to find the comment"));
    }

    public Message removeComment(int id)
    {
        try
        {
            logger.info("Deleting comment : {}",id);
            commentRepository.deleteById(id);
            return new Message(MessageStatus.SUCCESS,"Comment deleted");
        }
        catch (Exception exception)
        {
            logger.error("Exception occurred while deleting comment : {} - {}",id,exception.getMessage());
            return new Message(MessageStatus.FAILURE,"Unable to delete comment");
        }
    }

    public List<CommentDto> getAllComments(Post post)
    {
       List<Comment> comments=commentRepository.findAllByPost(post);
       List<CommentDto> commentsResponse= new ArrayList<>();
       logger.info("Parsing comments");
       comments.forEach(comment -> commentsResponse.add(parseComment(comment)));
       return commentsResponse;
    }

    private CommentDto parseComment(Comment comment)
    {
        CommentDto commentDto=new CommentDto();
        commentDto.comment_id=comment.getCommentId();
        commentDto.comment_text=comment.getCommentText();
        commentDto.comment_time=comment.getCommentTime();
        commentDto.user_email=comment.getUser().getEmail();
        return commentDto;
    }
}
