package com.example.taskmanager.demo;

import com.example.taskmanager.demo.model.Task;
import com.example.taskmanager.demo.repository.TaskRepository;
import com.example.taskmanager.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTests {

    @Mock
    private TaskRepository taskRepo;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Date today = Calendar.getInstance().getTime();

    //Postive test cases

    @Test
    void createTask() {
        Task task = new Task("Test Task", "Description", "TO DO", today, "HIGH","user1@gmail.com");
        when(taskRepo.save(any(Task.class))).thenReturn(task);
        when(taskRepo.findById(any(Long.class))).thenReturn(Optional.of(task));

        Task createdTask = taskService.createTask(task);

        assertThat(createdTask.getTitle()).isEqualTo("Test Task");
        assertThat(createdTask.getPriority()).isEqualTo("HIGH");
        assertThat(createdTask.getDescription()).isEqualTo("Description");
        assertThat(createdTask.getStatus()).isEqualTo("TO DO");
    }

    @Test
    void getTaskByID() {
        Task task = new Task("Test Task", "Description", "TO DO", null, "HIGH","user1@gmail.com");
        when(taskRepo.findById(any(Long.class))).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskByID(1);

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getTitle()).isEqualTo("Test Task");
        assertThat(foundTask.getPriority()).isEqualTo("HIGH");
        assertThat(foundTask.getDescription()).isEqualTo("Description");
        assertThat(foundTask.getStatus()).isEqualTo("TO DO");
    }

    @Test
    void getAllTasks() {
        Task[] tasks = new Task[]{new Task("Test Task1", "Description", "TO DO", null, "HIGH","user1@gmail.com"),
                new Task("Test Task2", "Description", "TO DO", null, "HIGH","user1@gmail.com")};
        when(taskRepo.findAll()).thenReturn(List.of(tasks));

        Iterable<Task> allTasks = taskService.getAllTasks();

        assertThat(allTasks).isNotNull();
        verify(taskRepo, times(1)).findAll();
    }

    @Test
    void updateTask() {
        Task task = new Task("Test Task", "Description", "TO DO", null, "HIGH","user1@gmail.com");
        Task updatedTask = new Task("Updated Task", "Updated Description", "IN PROGRESS", null, "MEDIUM","user1@gmail.com");
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepo.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertThat(result.getTitle()).isEqualTo("Updated Task");
        assertThat(result.getPriority()).isEqualTo("MEDIUM");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.getStatus()).isEqualTo("IN PROGRESS");

        verify(taskRepo, times(1)).save(task);
    }

    @Test
    void deleteTask() {
        Task task = new Task("Test Task", "Description", "TO DO", null, "HIGH","user1@gmail.com");
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepo, times(1)).deleteById(1L);
    }

    // Negative test cases

    @Test
    void getTaskByID_NotFound() {
        when(taskRepo.findById(1L)).thenReturn(Optional.empty());

        Task foundTask = taskService.getTaskByID(1L);

        assertThat(foundTask).isNull();
    }

    @Test
    void updateTask_NotFound() {
        Task updatedTask = new Task("Updated Task", "Updated Description", "IN PROGRESS", null, "MEDIUM","user1@gmail.com");
        when(taskRepo.findById(1L)).thenReturn(Optional.empty());

        assertThat(taskService.updateTask(10,updatedTask)).isNull();
    }

    @Test
    void updateTask_NullInput() {
        Task task = new Task("Test Task", "Description", "TO DO", null, "HIGH","user1@gmail.com");
        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
        assertThat(taskService.updateTask(1L, null)).isNull();
    }
}
