package com.example.taskmanager.demo;

import com.example.taskmanager.demo.model.User;
import com.example.taskmanager.demo.repository.UserRepository;
import com.example.taskmanager.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User("test","test","test@example.com", "password123");
        when(userRepo.save(user)).thenReturn(user);
        when(userRepo.findByEmail("test@example.com")).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    public void testCreateUserWithNullEmail() {
        User user = new User("test","test",null, "password123");

        assertNull(userService.createUser(user));
    }

    @Test
    public void testGetUserByEmail() {
        User user = new User("test","test","test@example.com", "password123");
        when(userRepo.findByEmail("test@example.com")).thenReturn(user);

        User foundUser = userService.getUserByEmail("test@example.com");
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testGetUserByEmailNotFound() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(null);

        assertNull(userService.getUserByEmail("test@example.com"));
    }

    @Test
    public void testGetAllUsers() {
        User[] users = new User[]{new User("test","test","test@example.com", "password123")};
        when(userRepo.findAll()).thenReturn(List.of(users));

        Iterable<User> result = userService.getAllUsers();
        assertNotNull(result);
        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User("test","test","test@example.com", "password123");
        User updatedUser = new User("test","test","test@example.com", "newPassword");
        when(userRepo.findByEmail("test@example.com")).thenReturn(existingUser);
        when(userRepo.save(existingUser)).thenReturn(existingUser);

        User result = userService.updateUser("test@example.com", updatedUser);
        assertNotNull(result);
        assertEquals("newPassword", result.getPassword());
    }

    @Test
    public void testUpdateUserNotFound() {
        User updatedUser = new User("test","test","test@example.com", "newPassword");
        when(userRepo.findByEmail("test@example.com")).thenReturn(null);

        assertNull( userService.updateUser("test@example.com", updatedUser));
    }
}
