package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
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
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {
        User user2 = new User(2L, "user2", "user2@example.com", "User Two", true);
        when(userService.getAllUsers()).thenReturn(Arrays.asList(testUser, user2));
        
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].username").value("testuser"))
            .andExpect(jsonPath("$[1].username").value("user2"));
        
        verify(userService).getAllUsers();
    }
    
    @Test
    void getActiveUsers_ShouldReturnActiveUsers() throws Exception {
        when(userService.getActiveUsers()).thenReturn(Arrays.asList(testUser));
        
        mockMvc.perform(get("/api/users/active"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].active").value(true));
        
        verify(userService).getActiveUsers();
    }
    
    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(testUser));
        
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("testuser"));
        
        verify(userService).getUserById(1L);
    }
    
    @Test
    void getUserById_WhenUserNotExists_ShouldReturn404() throws Exception {
        when(userService.getUserById(999L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound());
        
        verify(userService).getUserById(999L);
    }
    
    @Test
    void createUser_WhenValidUser_ShouldReturnCreatedUser() throws Exception {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        newUser.setFullName("New User");
        
        User createdUser = new User(3L, "newuser", "new@example.com", "New User", true);
        when(userService.createUser(any(User.class))).thenReturn(createdUser);
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.username").value("newuser"));
        
        verify(userService).createUser(any(User.class));
    }
    
    @Test
    void createUser_WhenInvalidUser_ShouldReturn400() throws Exception {
        User invalidUser = new User();
        invalidUser.setUsername(""); // Invalid: blank username
        invalidUser.setEmail("invalid-email"); // Invalid email format
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
            .andExpect(status().isBadRequest());
        
        verify(userService, never()).createUser(any());
    }
    
    @Test
    void updateUser_WhenUserExists_ShouldReturnUpdatedUser() throws Exception {
        User updatedUser = new User(1L, "updated", "updated@example.com", "Updated User", false);
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);
        
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("updated"));
        
        verify(userService).updateUser(eq(1L), any(User.class));
    }
    
    @Test
    void deleteUser_WhenUserExists_ShouldReturn204() throws Exception {
        doNothing().when(userService).deleteUser(1L);
        
        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent());
        
        verify(userService).deleteUser(1L);
    }
    
    @Test
    void deleteUser_WhenUserNotExists_ShouldReturn404() throws Exception {
        doThrow(new IllegalArgumentException("User not found")).when(userService).deleteUser(999L);
        
        mockMvc.perform(delete("/api/users/999"))
            .andExpect(status().isNotFound());
        
        verify(userService).deleteUser(999L);
    }
}