package com.roopa.social_media.authentication.repository;

import com.roopa.social_media.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer>
{
    public User findByEmail(String email);
}