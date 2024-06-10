package com.example.taskmanager.demo.repository;

import com.example.taskmanager.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
