package uz.scripteone.userauth.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.scripteone.userauth.dto.UserCreateDto;
import uz.scripteone.userauth.entity.Role;
import uz.scripteone.userauth.entity.User;
import uz.scripteone.userauth.exception.BadRequestException;
import uz.scripteone.userauth.exception.FileNotFoundException;
import uz.scripteone.userauth.repository.RoleRepository;
import uz.scripteone.userauth.repository.UserRepository;
import uz.scripteone.userauth.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }


    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new FileNotFoundException("Not Found User with ID: " + userId));
    }


    @Override
    public User updateUser(Long userId, UserCreateDto dto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (!user.getFirstname().equals(dto.getFirstname())) {
                user.setFirstname(dto.getFirstname());
            }

            if (!user.getLastname().equals(dto.getLastname())) {
                user.setLastname(dto.getLastname());
            }

            if (!user.getEmail().equals(dto.getEmail())) {
                user.setEmail(dto.getEmail());
            }

            if (!user.getPassword().equals(dto.getPassword())) {
                user.setPassword(dto.getPassword());
            }

            Set<Role> newRoleSet = new HashSet<>();
            Set<Long> roleIds1 = dto.getRoleIds();
            for (Long aLong : roleIds1) {
                Optional<Role> byId = roleRepository.findById(aLong);
                if (byId.isPresent()) {
                    Role role = byId.get();
                    newRoleSet.add(role);
                } else {
                    throw new BadRequestException("Not Found Role with ID: " + byId);
                }
            }

            Set<Role> roles = user.getRoles();
            roles.addAll(newRoleSet);
            user.setRoles(roles);

            return userRepository.save(user);
        }
        throw new BadRequestException("Not Found User with ID: " + userId);
    }

    @Override
    public String removeUser(Long userId) {
        userRepository.deleteById(userId);

        return "Successfully Deleted";
    }
}
