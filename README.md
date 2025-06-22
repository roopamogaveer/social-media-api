# Social Media API - Roopa 

# Spring Boot + JWT Security

REST API built with **Spring Boot** featuring:

- User authentication & authorization using **JWT**
- Operations for **Posts**, **Likes**, and **Comments**
- **Role-based access control**
- Layered architecture with DTOs, services, repositories
- Clean separation of concerns using packages and modules

---

## Project Structure

```
src
└── main
    ├── java
    │   └── com.roopa.social_media
    │       ├── authentication
    │       │   ├── config
    │       │   │   ├── SecurityConfig.java
    │       │   │   ├── UserInfoDetails.java
    │       │   │   └── UserInfoDetailsService.java
    │       │   ├── controller
    │       │   │   └── AuthController.java
    │       │   ├── dto
    │       │   │   ├── AuthRequest.java
    │       │   │   └── Message.java
    │       │   ├── filter
    │       │   │   └── AuthFilter.java
    │       │   ├── model
    │       │   │   └── User.java
    │       │   ├── repository
    │       │   │   └── UserRepository.java
    │       │   └── service
    │       │       ├── AuthServer.java
    │       │       └── JwtService.java
    │       ├── core
    │       │   ├── constant
    │       │   │   └── AppConstant.java
    │       │   └── enums
    │       │       └── MessageStatus.java
    │       ├── post
    │       │   ├── controller
    │       │   │   ├── PostController.java
    │       │   │   └── CommentController.java
    │       │   ├── dto
    │       │   │   ├── CommentDto.java
    │       │   │   └── PostDto.java
    │       │   ├── model
    │       │   │   ├── Comment.java
    │       │   │   ├── Like.java
    │       │   │   └── Post.java
    │       │   ├── repository
    │       │   │   ├── CommentRepository.java
    │       │   │   ├── LikeRepository.java
    │       │   │   └── PostRepository.java
    │       │   └── service
    │       │       ├── CommentService.java
    │       │       └── PostService.java
    │       └── SocialMediaApplication.java
    └── resources
        └── application.properties
```

---

## Features

### Authentication & Security

- JWT-based stateless authentication
- Custom user details service
- Password encryption with BCrypt
- Request filtering with `AuthFilter`
- Endpoint access control via `SecurityConfig`

###  User Management

- Register new users
- Authenticate users and return JWT token
- Basic CRUD operations via `AuthController`

### Posts

- Create, read, delete posts
- Like/unlike posts

###  Comments

- Add, delete comments on posts
- Retrieve all comments for a post

---

##  Tech Stack

| Layer        | Tech/Tool                   |
| ------------ |-----------------------------|
| Backend      | Spring Boot, Spring Security |
| JWT          | For Authentication          |
| ORM          | Spring Data JPA             |
| DB           | H2 in-memory                |
| Build Tool   | Maven                       |

---

##  Configuration

Edit the `application.properties` for:

```properties
# App name and port
spring.application.name=social-media
server.port=7878

# H2 DB configs
spring.datasource.url=jdbc:h2:mem:socialMedia
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=roopa
spring.datasource.password=password
spring.jpa.defer-datasource-initialization=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false

# JWT configs
jwt.secret = 5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437
jwt.expiration=3600000

# Logging 
logging.file.name=Application.log
```

---

##  Running the App

### Prerequisites:

- JDK 17+
- Maven

### Steps:

```bash
# Clone the repository
git clone https://github.com/roopamogaveer/social-media-api.git
cd social-media-api

# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

---

## API Endpoints

### AuthController (`/api/v1/users`)

| Method | Endpoint        | Description             |
|--------|-----------------|-------------------------|
| POST   | `/create`       | Register a new user     |
| DELETE | `/{id}`         | Delete user             |
| POST   | `/authenticate` | Login and get JWT token |
| GET    | `/`             | Get list of users       |
| GET    | `/{id}`         | Get specific user       |

### PostController (`/api/v1/posts`)

| Method | Endpoint         | Description           |
|--------|------------------|-----------------------|
| POST   | `/`              | Create a new post     |
| GET    | `/`              | Get all posts         |
| DELETE | `/{postId}`      | Delete a post         |
| POST   | `/like/{postId}` | Like or unlike a post |

### CommentController (`/api/v1/comments`)

| Method | Endpoint         | Description             |
| ------ |------------------| ----------------------- |
| POST   | `/`              | Add a comment to a post |
| GET    | `/post/{postId}` | Get comments for a post |
| DELETE | `/{commentId}`   | Delete a comment        |


>  All secured endpoints require a valid JWT in the `Authorization` header:
>
> ```
> Authorization: Bearer <your_token>
> ```

---

## Testing

You can use **Postman** to test endpoints. Make sure to:

- First register/login to get a JWT
- Pass the JWT in headers for protected endpoints
- H2 console running in `/h2-console`
- JDBC URL=jdbc:h2:mem:socialMedia
- Username=roopa
- Password=password
---


## Author

**Roopa**\
Spring Boot and Dot Net developer

