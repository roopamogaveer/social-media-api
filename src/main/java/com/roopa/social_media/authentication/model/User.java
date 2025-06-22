package com.roopa.social_media.authentication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roopa.social_media.post.model.Post;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="social_media_user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    public String email;
    public String role;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public List<Post> posts;


    public User( String name, String password, String email, String role)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User()
    {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}