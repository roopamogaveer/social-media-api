package com.roopa.social_media.post.repository;

import com.roopa.social_media.post.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository  extends JpaRepository<Like,Integer>
{
    boolean existsByLikedUserAndPostId(String likedUser, int postId);
    Like findByLikedUserAndPostId(String likedUser, int postId);
}
