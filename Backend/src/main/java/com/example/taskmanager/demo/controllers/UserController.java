package com.example.taskmanager.demo.controllers;

import com.example.taskmanager.demo.model.User;
import com.example.taskmanager.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public User create(@RequestBody User user){
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
        return userService.createUser(user);
    }

    @GetMapping("/users/{email}")
    public User getUser(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return user;
    }

    @GetMapping("/users")
    public Iterable<User> getUsers(){
        return userService.getAllUsers();
    }

    @PutMapping("/users/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User user){
        User old_user = userService.getUserByEmail(email);
        if (old_user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userService.updateUser(email,user);
    }

    @DeleteMapping("users/{email}")
    public void deleteUser(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        userService.deleteUser(email);
    }

    @PostMapping(path = "/users/login")
    public User login(@RequestBody User auth_user){
        User user = userService.getUserByEmail(auth_user.getEmail());
        if(user!=null && user.getPassword().equals(auth_user.getPassword()))
            return user;
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
