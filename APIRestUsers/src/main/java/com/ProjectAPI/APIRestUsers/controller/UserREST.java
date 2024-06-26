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
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody User user){
            User newUser = service.addUser(user);
            String responseMessage = "User added successfully!";
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

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


//    @PutMapping("/{id}")
//    public  ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user){
//        User updatedUser = service.updateUser(id, user);
//        return ResponseEntity.ok(updatedUser);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            User updatedUser = service.updateUser(id, user);
            if (updatedUser != null) {
                return ResponseEntity.ok("User details updated successfully!");
            } else {
                ErrorHandler thisError = new ErrorHandler();
                thisError.setErrorCode(HttpStatus.NOT_FOUND.value());
                thisError.setErrorMessage("Sorry, User not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(thisError);
            }
        } catch (Exception e) {
            ErrorHandler thisError = new ErrorHandler();
            thisError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            thisError.setErrorMessage(e.getMessage() + customMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(thisError);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        if(service.isUserExists(id)) {
            service.deleteUser(id);
            return ResponseEntity.ok("The User has been correctly eliminated!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + id + " not found");
        }
    }
}

