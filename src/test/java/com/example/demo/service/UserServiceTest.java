package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setFullName("Test User");
        testUser.setActive(true);
    }
    
    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user2 = new User(2L, "user2", "user2@example.com", "User Two", true);
        List<User> users = Arrays.asList(testUser, user2);
        
        when(userRepository.findAll()).thenReturn(users);
        
        List<User> result = userService.getAllUsers();
        
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(testUser, user2);
        verify(userRepository).findAll();
    }
    
    @Test
    void getActiveUsers_ShouldReturnOnlyActiveUsers() {
        User inactiveUser = new User(2L, "inactive", "inactive@example.com", "Inactive User", false);
        List<User> activeUsers = Arrays.asList(testUser);
        
        when(userRepository.findByActiveTrue()).thenReturn(activeUsers);
        
        List<User> result = userService.getActiveUsers();
        
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getActive()).isTrue();
        verify(userRepository).findByActiveTrue();
    }
    
    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        
        Optional<User> result = userService.getUserById(1L);
        
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUser);
        verify(userRepository).findById(1L);
    }
    
    @Test
    void getUserByUsername_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        
        Optional<User> result = userService.getUserByUsername("testuser");
        
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testUser);
        verify(userRepository).findByUsername("testuser");
    }
    
    @Test
    void createUser_WhenValidUser_ShouldReturnCreatedUser() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        User result = userService.createUser(testUser);
        
        assertThat(result).isEqualTo(testUser);
        verify(userRepository).save(testUser);
    }
    
    @Test
    void createUser_WhenUsernameExists_ShouldThrowException() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
        
        assertThatThrownBy(() -> userService.createUser(testUser))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username already exists");
        
        verify(userRepository, never()).save(any());
    }
    
    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() {
        User updatedDetails = new User();
        updatedDetails.setUsername("updateduser");
        updatedDetails.setEmail("updated@example.com");
        updatedDetails.setFullName("Updated User");
        updatedDetails.setActive(false);
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByUsername("updateduser")).thenReturn(false);
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        
        User result = userService.updateUser(1L, updatedDetails);
        
        assertThat(result).isNotNull();
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        
        userService.deleteUser(1L);
        
        verify(userRepository).deleteById(1L);
    }
    
    @Test
    void deleteUser_WhenUserNotExists_ShouldThrowException() {
        when(userRepository.existsById(999L)).thenReturn(false);
        
        assertThatThrownBy(() -> userService.deleteUser(999L))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("User not found with id: 999");
        
        verify(userRepository, never()).deleteById(any());
    }
}