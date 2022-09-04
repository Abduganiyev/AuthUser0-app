package uz.scripteone.userauth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.scripteone.userauth.dto.LoginDto;
import uz.scripteone.userauth.dto.RegisterDto;
import uz.scripteone.userauth.dto.response.Response;
import uz.scripteone.userauth.entity.Role;
import uz.scripteone.userauth.entity.User;
import uz.scripteone.userauth.repository.RoleRepository;
import uz.scripteone.userauth.repository.UserRepository;
import uz.scripteone.userauth.security.JwtProvider;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
    }

    public Response register(RegisterDto dto) {
        User user = new User();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());

        Set<Role> roleSet = new HashSet<>();
        for (Long roleId : dto.getRoleIdSet()) {
            Optional<Role> roleOptional = roleRepository.findById(roleId);
            roleOptional.ifPresent(roleSet::add);
        }
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User save = userRepository.save(user);
        return new Response(true,"Successfully registered",save.getEmail());
    }

    public Response login(LoginDto dto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        String generateToken = jwtProvider.generateToken(principal.getEmail(), principal.getRoles());
        return new Response(true, "Token", generateToken);
    }
}
