package com.ProjectAPI.APIRestUsers.service.impl;

import com.ProjectAPI.APIRestUsers.entity.User;
import com.ProjectAPI.APIRestUsers.repository.UserRepo;
import com.ProjectAPI.APIRestUsers.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ServiceException("No User found with that ID"));
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepo.findById(id).orElseThrow(() -> new ServiceException("No User found with that ID"));

        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setRole(user.getRole());

        return userRepo.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
