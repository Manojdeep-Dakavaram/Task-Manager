package com.example.taskmanager.demo.controllers;

import com.example.taskmanager.demo.model.Task;
import com.example.taskmanager.demo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String hello(){
        return "Welcome to Task Manager";
    }

    @PostMapping(path = "/tasks")
    public Task create(@RequestBody Task task){
        if (task == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return taskService.createTask(task);
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable long id){
        Task task = taskService.getTaskByID(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return task;
    }

    @GetMapping("/tasks")
    public Iterable<Task> getTasks(){
        return taskService.getAllTasks();
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable long id, @RequestBody Task task){
        Task old_task = taskService.getTaskByID(id);
        if (old_task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return taskService.updateTask(id,task);
    }

    @DeleteMapping("tasks/{id}")
    public void deleteTask(@PathVariable long id){
        Task task = taskService.getTaskByID(id);
        if (task == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        taskService.deleteTask(id);
    }
}
