package com.roopa.social_media.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.roopa.social_media.core.constant.AppConstant;

@Service
public class JwtService
{

    public static String SECRET;
    public static long EXPIRATION;

    Logger logger= LoggerFactory.getLogger(JwtService.class);

    public JwtService(@Value(AppConstant.JWT_SECRET_PROPERTY) String SECRET, @Value(AppConstant.JWT_EXPIRATION_PROPERTY) long EXPIRATION)
    {
        JwtService.SECRET=SECRET;
        JwtService.EXPIRATION=EXPIRATION;
    }

    public String generateToken(String email)
    {
        logger.info("Generating JWT");
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,email);
    }


    private String createToken(Map<String,Object> claims,String email)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(signInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key signInKey()
    {
        byte[] secretByte= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(secretByte);
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        logger.info("Validating JWT");
        return getUsername(token).equals(userDetails.getUsername()) && !getExpiration(token).before(new Date());
    }

    public String getUsername(String token)
    {
        return extractClaims(token,Claims::getSubject);
    }

    private Date getExpiration(String token)
    {
        return extractClaims(token,Claims::getExpiration);
    }



    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(signInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}