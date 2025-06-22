package com.roopa.social_media.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CommentDto
{
    public int comment_id;
    public String comment_text;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public int post_id;

    public String user_email;
    public LocalDateTime comment_time;
}
