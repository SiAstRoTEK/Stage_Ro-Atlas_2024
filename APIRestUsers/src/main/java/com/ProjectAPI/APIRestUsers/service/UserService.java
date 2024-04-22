package com.ProjectAPI.APIRestUsers.service;

import com.ProjectAPI.APIRestUsers.entity.User;

import java.util.List;

public interface UserService {

    User addUser(User user);
    User getUser(Long id);
    List<User> getAllUser();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
