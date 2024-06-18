package com.example.taskmanager.demo;

import com.example.taskmanager.demo.model.Task;
import com.example.taskmanager.demo.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    private Date today = Calendar.getInstance().getTime();

    @AfterEach
    void showAlltasks(){
        for(Task task:taskRepository.findAll())
            System.out.println(task.getId()+"-"+task.getTitle());
    }


    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        String taskJson = "{\"title\": \"New Task\", \"description\": \"Task Description\", \"status\": \"TO DO\", \"priority\": \"HIGH\",\"email\": \"user1@gmail.com\"}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("New Task")))
                .andExpect(jsonPath("$.description", is("Task Description")))
                .andExpect(jsonPath("$.status", is("TO DO")))
                .andExpect(jsonPath("$.priority", is("HIGH")));
    }

    @Test
    void getTask_ShouldReturnTask() throws Exception {
        Task task = new Task("Existing Task", "Existing Description", "TO DO", null, "HIGH","user1@gmail.com");
        task = taskRepository.save(task);

        mockMvc.perform(get("/tasks/user1@gmail.com/" + task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Existing Task")))
                .andExpect(jsonPath("$.description", is("Existing Description")))
                .andExpect(jsonPath("$.status", is("TO DO")))
                .andExpect(jsonPath("$.priority", is("HIGH")));
    }

    @Test
    void getTask_NotFound() throws Exception {
        mockMvc.perform(get("/tasks/999"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        Task task = new Task("Existing Task", "Existing Description", "TO DO", null, "HIGH","user1@gmail.com");
        task = taskRepository.save(task);

        String updatedTaskJson = "{\"title\": \"Updated Task\", \"description\": \"Updated Description\", \"status\": \"IN PROGRESS\", \"priority\": \"MEDIUM\",\"email\": \"user1@gmail.com\"}";

        mockMvc.perform(put("/tasks/user1@gmail.com/" + task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Task")))
                .andExpect(jsonPath("$.description", is("Updated Description")))
                .andExpect(jsonPath("$.status", is("IN PROGRESS")))
                .andExpect(jsonPath("$.priority", is("MEDIUM")));
    }

    @Test
    void updateTask_NotFound() throws Exception {
        String updatedTaskJson = "{\"title\": \"Updated Task\", \"description\": \"Updated Description\", \"status\": \"IN PROGRESS\", \"priority\": \"MEDIUM\",\"email\": \"user1@gmail.com\"}";

        mockMvc.perform(put("/tasks/user1@gmailcom/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedTaskJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_ShouldReturnOk() throws Exception {
        Task task = new Task("Task to be deleted", "Delete Description", "TO DO", null, "HIGH","user1@gmail.com");
        task = taskRepository.save(task);

        mockMvc.perform(delete("/tasks/user1@gmail.com/" + task.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTask_NotFound() throws Exception {
        mockMvc.perform(delete("/tasks/user1@gmail.com/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTask_NullTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
