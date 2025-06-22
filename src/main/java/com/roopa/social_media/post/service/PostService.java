package com.roopa.social_media.post.service;

import com.roopa.social_media.authentication.dto.Message;
import com.roopa.social_media.authentication.service.AuthService;
import com.roopa.social_media.core.enums.MessageStatus;
import com.roopa.social_media.post.model.Like;
import com.roopa.social_media.post.model.Post;
import com.roopa.social_media.post.repository.LikeRepository;
import com.roopa.social_media.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService
{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    Logger logger= LoggerFactory.getLogger(AuthService.class);

    public Message addPost(Post post)
    {
        try
        {
            logger.info("Creating a Post");
            postRepository.save(post);
            return new Message(MessageStatus.SUCCESS,"Posted successfully");
        }
        catch (Exception exception)
        {
            logger.error("Exception occurred while creating a post : {} ",exception.getMessage());
            return new Message(MessageStatus.FAILURE,"Unable to post");
        }

    }

    public Message removePost(int id)
    {
        try
        {
            logger.info("Deleting Post : {}",id);
            postRepository.deleteById(id);
            return new Message(MessageStatus.SUCCESS,"Post deleted successfully");
        }
        catch (Exception exception)
        {
            logger.error("Exception occurred while deleting post : {} - {}",id,exception.getMessage());
            return new Message(MessageStatus.FAILURE,"Unable to delete post");
        }

    }


    public List<Post> allPosts()
    {
        return postRepository.findAll();
    }

    public Post getPost(int id)
    {
        return postRepository.findById(id).orElseThrow(()-> new RuntimeException("Post not found"));
    }

    public Post like(int post_id,String email)
    {

        logger.info("Liking post : {}",post_id);
        if(likeRepository.existsByLikedUserAndPostId(email,post_id))
        {
            logger.info("User already liked this post");
            Like like=likeRepository.findByLikedUserAndPostId(email,post_id);
            likeRepository.deleteById(like.getLikeId());
            Post post=postRepository.findById(post_id).orElseThrow(()-> new RuntimeException("Post not found"));
            post.setLikes(post.getLikes()-1);
            postRepository.save(post);
            logger.info("Post has been disliked");
            return post;
        }

        Post post=postRepository.findById(post_id).orElseThrow(()-> new RuntimeException("Post not found"));
        Like like=new Like(email,post_id);
        likeRepository.save(like);
        post.setLikes(post.getLikes()+1);
        postRepository.save(post);
        logger.info("Post has been liked");
        return post;
    }


}
