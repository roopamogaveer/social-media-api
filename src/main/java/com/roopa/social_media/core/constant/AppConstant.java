package com.roopa.social_media.core.constant;


public class AppConstant
{
    public static final String[] ALLOWED_PATH= new String[]{"/api/v1/users/create","/api/v1/users/authenticate","/h2-console/**"};
    public static final String[] CSRF_ALLOWED_PATH= new String[]{"/api/v1/users/**","/h2-console/**","/api/v1/posts/**","/api/v1/comments/**"};
    public static final String[] RESTRICTED_PATH=new String[]{"/api/v1/users/**","/api/v1/posts/**","/api/v1/comments/**"};
    public static final String AUTHORIZATION="Authorization";
    public static final String BEARER="Bearer ";
    public static final String JWT_SECRET_PROPERTY="${jwt.secret}";
    public static final String JWT_EXPIRATION_PROPERTY="${jwt.expiration}";
}
