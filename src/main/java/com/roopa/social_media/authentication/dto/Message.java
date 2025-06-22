package com.roopa.social_media.authentication.dto;


import com.roopa.social_media.core.enums.MessageStatus;

public class Message
{

    public String status;
    public String message;


    public Message(MessageStatus status, String message)
    {
        this.status = String.valueOf(status);
        this.message = message;
    }

    public Message()
    {
    }


}