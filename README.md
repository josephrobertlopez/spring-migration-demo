# Spring Boot 2 to 3 Migration Demo

This repository demonstrates the migration process from Spring Boot 2 (JDK 1.8) to Spring Boot 3 (JDK 17).

## Repository Structure

- **main branch**: Contains the original Spring Boot 2.7.18 application running on JDK 1.8
- **feature/spring-boot-3-migration branch**: Contains the migrated Spring Boot 3.2.0 application running on JDK 17

## Application Overview

The demo application is a simple REST API for user management with the following features:
- CRUD operations for User entities
- H2 in-memory database
- Comprehensive unit tests using JUnit and Mockito
- Integration tests using Cucumber
- RESTful endpoints with validation

## Migration Steps

### 1. Update Maven Dependencies (pom.xml)
- Updated Spring Boot parent version from 2.7.18 to 3.2.0
- Changed Java version from 1.8 to 17
- Updated Cucumber version to maintain compatibility

### 2. Package Name Changes
- Replaced `javax.persistence.*` with `jakarta.persistence.*`
- Replaced `javax.validation.*` with `jakarta.validation.*`

### 3. Testing Framework Updates
- Spring Boot 3 testing annotations remain mostly the same
- `@LocalServerPort` is already in the correct package for Spring Boot 3

## Running the Application

### Main Branch (Spring Boot 2)
```bash
git checkout main
mvn clean install
mvn spring-boot:run
```
Requirements: JDK 1.8

### Migration Branch (Spring Boot 3)
```bash
git checkout feature/spring-boot-3-migration
mvn clean install
mvn spring-boot:run
```
Requirements: JDK 17

## API Endpoints

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/username/{username}` - Get user by username
- `GET /api/users/active` - Get active users
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## Running Tests

### Unit Tests
```bash
mvn test
```

### Cucumber Integration Tests
```bash
mvn test -Dtest=CucumberTestRunner
```

## Key Migration Considerations

1. **JDK Version**: Ensure you have JDK 17 installed for Spring Boot 3
2. **Dependencies**: Some third-party libraries may need updates for Jakarta EE compatibility
3. **Configuration**: Most Spring Boot configuration properties remain the same
4. **Security**: If using Spring Security, additional migration steps may be required
5. **WebMvcTest**: Test annotations work the same way in Spring Boot 3

## Common Issues and Solutions

1. **Import Errors**: Replace all `javax.*` imports with `jakarta.*`
2. **Dependency Conflicts**: Check for transitive dependencies that may need exclusions
3. **Deprecated APIs**: Review Spring Boot 3 migration guide for deprecated features

## Resources

- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Jakarta EE Migration](https://jakarta.ee/resources/JakartaEE-Datasheet.pdf)
- [JDK 17 Features](https://openjdk.org/projects/jdk/17/)