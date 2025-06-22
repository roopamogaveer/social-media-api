package com.roopa.social_media.post.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roopa.social_media.authentication.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="social_media_comment")
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int commentId;

    public String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id",nullable = false)
    @JsonBackReference
    public User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    @JsonBackReference
    public Post post;

    public LocalDateTime commentTime;

    public Comment(User user, Post post,String comment_text ,LocalDateTime comment_time) {
        this.user = user;
        this.post = post;
        this.commentTime = comment_time;
        this.commentText =comment_text;
    }

    public Comment()
    {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }
}
