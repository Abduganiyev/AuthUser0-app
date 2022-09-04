package uz.scripteone.userauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.scripteone.userauth.dto.UserCreateDto;
import uz.scripteone.userauth.entity.User;
import uz.scripteone.userauth.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAllUser();
    }


    @GetMapping("/{user_id}")
    public User findById(@PathVariable("user_id") Long userId) {
        return userService.findUserById(userId);
    }

    @PutMapping("/{user_id}")
    public User edit(@PathVariable("user_id") Long userId,
                     @RequestBody UserCreateDto dto) {
        return userService.updateUser(userId, dto);
    }

    @DeleteMapping("/{user_id}")
    public String remove(@PathVariable("user_id") Long userId) {
        return userService.removeUser(userId);
    }


}
