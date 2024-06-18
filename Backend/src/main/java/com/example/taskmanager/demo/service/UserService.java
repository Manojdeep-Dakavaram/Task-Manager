package com.example.taskmanager.demo.service;

import com.example.taskmanager.demo.model.User;
import com.example.taskmanager.demo.repository.TaskRepository;
import com.example.taskmanager.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService( UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user ){
        if (user.getEmail() == null || user.getPassword() == null) {
            return null;
        }
        User created_user = userRepo.save(user);
        return getUserByEmail(user.getEmail());
    }

    public User getUserByID(String email){
        return userRepo.findByEmail(email);
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }

    public Iterable<User> getAllUsers(){
        return userRepo.findAll();
    }

    public User updateUser(String email, User user){
        User oldUser = getUserByID(email);
        if(oldUser == null || user == null)
            return null;

        userRepo.save(user);
        return user;
    }

    public void deleteUser(String email){
        userRepo.deleteById(email);
    }
}
