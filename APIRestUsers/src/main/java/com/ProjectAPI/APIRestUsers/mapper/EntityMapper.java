package com.ProjectAPI.APIRestUsers.mapper;

import com.ProjectAPI.APIRestUsers.DTO.UserDTO;
import com.ProjectAPI.APIRestUsers.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EntityMapper {

    public static UserDTO mapToUserDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public static List<UserDTO> mapToAllUserDto(List<User> allUser){
        List<UserDTO> allUserDTO = new ArrayList<>();
        for(User user : allUser) {
            UserDTO userDTO = new UserDTO();

            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setUsername(user.getUsername());
            userDTO.setEmail(user.getEmail());
            allUserDTO.add(userDTO);
        }

        return allUserDTO;
    }
}
