package com.roopa.social_media.post.model;

import jakarta.persistence.*;

@Entity
@Table(name="social_media_like")
public class Like
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int likeId;

    public String likedUser;
    public int postId;


    public Like(String liked_user, int post_id)
    {
        this.likedUser = liked_user;
        this.postId =post_id;
    }

    public Like()
    {

    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int like_id) {
        this.likeId = like_id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getLikedUser()
    {
        return likedUser;
    }

    public void setLikedUser(String likedUser)
    {
        this.likedUser = likedUser;
    }
}
