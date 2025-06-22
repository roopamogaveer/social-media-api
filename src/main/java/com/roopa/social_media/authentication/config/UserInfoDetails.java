package com.roopa.social_media.authentication.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.roopa.social_media.authentication.model.User;

/*
- User class which implements UserDetails class of spring security. It's used to create and access users
*/

public class UserInfoDetails implements UserDetails {

    int id;
    String name;
    String password;
    String email;
    List<GrantedAuthority> role;

    public UserInfoDetails(User user)
    {
        id=user.id;
        name=user.name;
        password=user.password;
        email=user.email;
        role= Arrays.stream(user.role.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}