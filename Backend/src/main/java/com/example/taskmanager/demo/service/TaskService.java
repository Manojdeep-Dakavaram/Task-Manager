package com.example.taskmanager.demo.service;

import com.example.taskmanager.demo.model.Task;
import com.example.taskmanager.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepo;

    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task createTask(Task task ){
        Task created_task = taskRepo.save(task);
        return getTaskByID(task.getId());
    }

    public Task getTaskByID(long id){
        return taskRepo.findById(id).orElse(null);
    }

    public Iterable<Task> getAllTasks(){
        return taskRepo.findAll();
    }

    public Task updateTask(long id, Task task){
        Task oldTask = getTaskByID(id);
        if(oldTask == null || task == null)
            return null;
        oldTask.setDescription(task.getDescription());
        oldTask.setPriority(task.getPriority());
        oldTask.setStatus(task.getStatus());
        oldTask.setTitle(task.getTitle());
        oldTask.setDueDate(task.getDueDate());
        taskRepo.save(oldTask);
        return oldTask;
    }

    public void deleteTask(long id){
        taskRepo.deleteById(id);
    }
}
