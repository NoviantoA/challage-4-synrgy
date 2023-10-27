package com.novianto.challage4.service.impl;

import com.novianto.challage4.dto.UserDto;
import com.novianto.challage4.entity.User;
import com.novianto.challage4.repository.UserRepository;
import com.novianto.challage4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Map<String, Object> saveUser(UserDto userDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (userDto != null) {
                User user = new User();
                user.setId(UUID.randomUUID());
                user.setEmailAddress(userDto.getEmailAddress());
                user.setUsername(userDto.getUsername());
                user.setPassword(userDto.getPassword());

                User saveUser = userRepository.save(user);

                response.put("success", true);
                response.put("message", "User berhasil disimpan");
                response.put("user", saveUser);
            } else {
                response.put("success", false);
                response.put("message", "Data UserDto tidak valid");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menyimpan user");
        }
        return response;
    }

    @Override
    public Map<String, Object> updateUser(UUID idUser, UserDto userDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> existingUser = Optional.ofNullable(userRepository.getByIdUser(idUser));

            if (existingUser.isPresent()) {
                User newUser = existingUser.get();
                newUser.setEmailAddress(userDto.getEmailAddress());
                newUser.setUsername(userDto.getUsername());
                newUser.setPassword(userDto.getPassword());

                User updatedUser = userRepository.save(newUser);

                response.put("success", true);
                response.put("message", "User berhasil diperbarui");
                response.put("user", updatedUser);
            } else {
                response.put("success", false);
                response.put("message", "User tidak ditemukan");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Gagal memperbarui user");
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteUser(UUID idUser) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> findUserOptional = Optional.ofNullable(userRepository.getByIdUser(idUser));

            if (findUserOptional.isPresent()) {
                User user = findUserOptional.get();
                userRepository.delete(user);
                response.put("success", true);
                response.put("message", "Data user ditemukan dan dihapus");
                response.put("data", user);
            } else {
                response.put("success", false);
                response.put("message", "Data user tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menghapus user");
        }
        return response;
    }

    @Override
    public Map<String, Object> getUserById(UUID idUser) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<User> findUserOptional = Optional.ofNullable(userRepository.getByIdUser(idUser));

            if (findUserOptional.isPresent()) {
                User user = findUserOptional.get();
                response.put("success", true);
                response.put("message", "Data user ditemukan");
                response.put("data", user);
            } else {
                response.put("success", false);
                response.put("message", "Data user tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal mengambil data user");
        }
        return response;
    }
}
