package uz.scripteone.userauth.service;

import uz.scripteone.userauth.dto.UserCreateDto;
import uz.scripteone.userauth.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAllUser();

    User findUserById(Long userId);

    User updateUser(Long userId, UserCreateDto dto);

    String removeUser(Long userId);
}
