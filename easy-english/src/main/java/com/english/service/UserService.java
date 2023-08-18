package com.english.service;

import com.english.dto.UserDto;
import com.english.entities.Role;
import com.english.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User findByEmail(String email);

    UserDto save(UserDto dto);

    UserDto findById(long id);

    UserDto update(UserDto nd);

    UserDto changePassword(UserDto dto, String newPassword);

    Page<UserDto> findAll(int page);

    List<UserDto> findByRole(Role role);

    void deleteById(long id);

}
