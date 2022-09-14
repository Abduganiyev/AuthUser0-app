package uz.scripteone.userauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.scripteone.userauth.dto.RegisterDto;
import uz.scripteone.userauth.service.impl.MyUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final MyUserService userService;

    @PostMapping("/addUser")
    public HttpEntity<?> register(@Valid @RequestBody RegisterDto dto){
        return userService.register(dto);
    }

    @GetMapping("/getAll")
    public HttpEntity<?> findAll() {
        return userService.findAll();
    }

/*    @PostMapping("/auth/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto dto,@ApiIgnore Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(userService.getErrors(errors));
        }
        return userService.login(dto);
    }*/
}
