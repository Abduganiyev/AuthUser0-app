package uz.scripteone.userauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.scripteone.userauth.dto.LoginDto;
import uz.scripteone.userauth.dto.RegisterDto;
import uz.scripteone.userauth.dto.response.Response;
import uz.scripteone.userauth.service.impl.MyUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MyUserService myUserService;

    @PostMapping("/register")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDto dto) {
        Response response = myUserService.register(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 500).body(response);
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto dto) {
        Response response = myUserService.login(dto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 500).body(response);
    }
}
