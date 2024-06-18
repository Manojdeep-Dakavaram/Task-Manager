package com.example.taskmanager.demo.repository;

import com.example.taskmanager.demo.model.Task;
import com.example.taskmanager.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(String email);
}
