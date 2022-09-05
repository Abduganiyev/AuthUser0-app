package uz.scripteone.userauth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import uz.scripteone.userauth.dto.LoginDto;
import uz.scripteone.userauth.dto.RegisterDto;
import uz.scripteone.userauth.dto.response.AuthenticationResponse;
import uz.scripteone.userauth.dto.response.Response;
import uz.scripteone.userauth.entity.Role;
import uz.scripteone.userauth.entity.User;
import uz.scripteone.userauth.repository.RoleRepository;
import uz.scripteone.userauth.repository.UserRepository;
import uz.scripteone.userauth.security.JwtProvider;

import java.time.Instant;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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

    public HttpEntity<?> register(RegisterDto dto) {
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
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail());
    }

    Long EXPIRATION_TIME = 18_000_000L;
    public HttpEntity<?> login(LoginDto dto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        User principal = (User) authenticate.getPrincipal();
        String generateToken = jwtProvider.generateToken(principal.getEmail(), principal.getRoles());

        AuthenticationResponse response = AuthenticationResponse.builder()
                .authenticationToken(generateToken)
                .username(dto.getEmail())
                .expirationData(Instant.now().plusMillis(EXPIRATION_TIME))
                .build();
        return ResponseEntity.ok(response);
    }

    public Map<String, String> getErrors(Errors errors) {
        Map<String, String> errorList = new HashMap<>();
        for (ObjectError error : errors.getAllErrors()) {

            String code = error.getCode();
            if(error.getCodes()!= null && error.getCodes().length > 0){
                code = error.getCodes()[0];
            }
            errorList.put(code, error.getDefaultMessage());
        }
        return errorList;
    }
}
