package com.roopa.social_media.post.repository;

import com.roopa.social_media.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Integer>
{
}