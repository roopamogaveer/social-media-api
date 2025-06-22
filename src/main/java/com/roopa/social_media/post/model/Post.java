package com.roopa.social_media.post.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.roopa.social_media.authentication.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="social_media_post")
public class Post
{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        public int postId;

        public String content;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="id",nullable = false)
        @JsonBackReference
        public User user;

        public int likes;

        public LocalDateTime postTime;

        @OneToMany(mappedBy = "post")
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        public List<Comment> comments;


    public Post( String content, User user, int likes, LocalDateTime post_time) {
        this.content = content;
        this.user = user;
        this.likes = 0;
    }

    public Post()
    {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return postId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }
}
