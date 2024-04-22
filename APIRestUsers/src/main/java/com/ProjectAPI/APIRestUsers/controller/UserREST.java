package com.ProjectAPI.APIRestUsers.controller;

import com.ProjectAPI.APIRestUsers.entity.User;
import com.ProjectAPI.APIRestUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/userlist")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UserREST {
    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser = service.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id){
        User foundUser = service.getUser(id);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = service.getAllUser();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
        User updatedUser = service.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        service.deleteUser(id);
        return ResponseEntity.ok("The User have been correctly eliminated!");
    }
}
