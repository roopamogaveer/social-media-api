package com.roopa.social_media.post.repository;

import com.roopa.social_media.post.model.Comment;
import com.roopa.social_media.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment,Integer>
{
    List<Comment> findAllByPost(Post post);
}