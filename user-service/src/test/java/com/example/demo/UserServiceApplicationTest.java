package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceApplicationTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .password("password123")
                .build();
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);
        User createdUser = userService.saveUser(sampleUser);
        assertEquals(sampleUser.getUsername(), createdUser.getUsername());
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(sampleUser);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userService.getAllUsers();
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        Optional<User> result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals("john_doe", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

}
