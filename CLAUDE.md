# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

FlowPay is a Spring Boot 3.4.4 REST API application using Java 21. The application provides user authentication and profile management functionality with JWT-based security.

## Architecture

- **Framework**: Spring Boot with Spring Security
- **Database**: H2 (in-memory for development)
- **Authentication**: JWT tokens using jjwt library
- **Package Structure**: `io.wil.flowpay` with standard Spring Boot layered architecture
  - `controller/` - REST endpoints
  - `service/` - Business logic
  - `model/` - JPA entities
  - `repository/` - Data access layer
  - `config/` - Spring configuration
  - `util/` - Utility classes (JWT handling)

## Development Commands

### Build and Run
```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Run the application
mvn spring-boot:run

# Package as JAR
mvn clean package
```

### Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=FlowpayApplicationTests

# Run with Spring Boot test profile
mvn test -Dspring.profiles.active=test
```

## Key Components

- **Main Application**: `FlowpayApplication.java` - Standard Spring Boot entry point
- **Security Configuration**: `SecurityConfig.java` - Defines BCrypt password encoder bean
- **User Management**: 
  - `UserController.java` - REST API endpoints for user operations
  - `UserService.java` - Business logic for user management
  - `User.java` - User entity model
  - `UserRepository.java` - JPA repository interface
- **JWT Utilities**: `JwtUtil.java` - JWT token generation and validation

## API Endpoints

Base path: `/api/users`
- `POST /create` - User registration
- `POST /login` - User authentication (returns JWT token)
- `GET /profile` - Get user profile (requires Authorization header)
- `PUT /profile` - Update user profile (requires Authorization header)

## Dependencies

Key dependencies include:
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- H2 Database (runtime)
- JJWT (JWT handling) - version 0.11.5
- Spring Boot DevTools (development)