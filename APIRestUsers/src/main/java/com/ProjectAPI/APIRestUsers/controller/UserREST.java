package com.ProjectAPI.APIRestUsers.controller;

import com.ProjectAPI.APIRestUsers.DTO.UserDTO;
import com.ProjectAPI.APIRestUsers.entity.ErrorHandler;
import com.ProjectAPI.APIRestUsers.entity.User;
import com.ProjectAPI.APIRestUsers.mapper.EntityMapper;
import com.ProjectAPI.APIRestUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${custom.message}")
    private String customMessage;

//    @PostMapping("/add")
//    public ResponseEntity<User> addUser(@RequestBody User user){
//        User newUser = service.addUser(user);
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") Long id){
        try {
            User foundUser = service.getUser(id);
            return new ResponseEntity<>(EntityMapper.mapToUserDto(foundUser), HttpStatus.OK);
        }catch (Exception e){
            ErrorHandler thisError = new ErrorHandler();
            thisError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            thisError.setErrorMessage(e.getMessage() + customMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(thisError);
        }

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> allUsers = service.getAllUser();
        return new ResponseEntity<>(EntityMapper.mapToAllUserDto(allUsers) , HttpStatus.OK);
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

