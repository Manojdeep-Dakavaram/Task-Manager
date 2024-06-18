package com.example.taskmanager.demo.repository;

import com.example.taskmanager.demo.model.Task;
import com.example.taskmanager.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Iterable<Task> findByEmail(String email);
}
