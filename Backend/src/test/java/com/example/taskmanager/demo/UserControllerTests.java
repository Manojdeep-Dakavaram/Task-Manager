package com.example.taskmanager.demo;

import com.example.taskmanager.demo.model.User;
import com.example.taskmanager.demo.repository.TaskRepository;
import com.example.taskmanager.demo.repository.UserRepository;
import com.example.taskmanager.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User("John", "Doe", "test@example.com", "password123");
    }

    @Test
    public void testCreateUser() throws Exception {
        //when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fname\":\"John\",\"lname\":\"Doe\",\"email\":\"test@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testGetUser() throws Exception {
        //when(userService.getUserByEmail(anyString())).thenReturn(user);

        mockMvc.perform(get("/users/user1@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user1@gmail.com"));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        //when(userService.getUserByEmail(anyString())).thenReturn(null);

        mockMvc.perform(get("/users/nonexistent@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        Iterable<User> users = List.of(user);
        //when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("user1@gmail.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {

        mockMvc.perform(put("/users/user1@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fname\":\"initial\",\"lname\":\"user1\",\"email\":\"user1@gmail.com\",\"password\":\"newPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user1@gmail.com"));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {

        mockMvc.perform(put("/users/nonexistent@example.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fname\":\"John\",\"lname\":\"Doe\",\"email\":\"nonexistent@example.com\",\"password\":\"newPassword\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUser() throws Exception {

        mockMvc.perform(delete("/users/test@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {

        mockMvc.perform(delete("/users/nonexistent@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testLoginSuccess() throws Exception {

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user2@gmail.com\",\"password\":\"user2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user2@gmail.com"));
    }

    @Test
    public void testLoginFailure() throws Exception {

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"nonexistent@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isNotFound());
    }
}
