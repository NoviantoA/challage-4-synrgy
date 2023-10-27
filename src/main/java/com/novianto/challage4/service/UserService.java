package com.novianto.challage4.service;

import com.novianto.challage4.dto.UserDto;
import com.novianto.challage4.entity.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {

    List<User> getAllUser();

    Map<String, Object> saveUser(UserDto userDto);

    Map<String, Object> updateUser(UUID idUser, UserDto userDto);

    Map<String, Object> deleteUser(UUID idUser);

    Map<String, Object> getUserById(UUID idUser);
}
