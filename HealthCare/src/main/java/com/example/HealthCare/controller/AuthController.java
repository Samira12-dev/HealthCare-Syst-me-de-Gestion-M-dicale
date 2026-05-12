package com.example.HealthCare.controller;

import com.example.HealthCare.config.JwtUtils;
import com.example.HealthCare.dto.LoginRequestDto;
import com.example.HealthCare.dto.RegisterRequestDto;
import com.example.HealthCare.entity.User;
import com.example.HealthCare.repository.UserRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @GetMapping("/test")
    public String test(){
        return "OK";
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto requestDto){
        if(userRepo.findByEmail(requestDto.getEmail()).isPresent()){
//            throw  new RuntimeException("Email already exists");
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = new User();
        user.setUserName(requestDto.getUserName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
       return ResponseEntity.ok(userRepo.save(user));
    }

@PostMapping("/login")
public String login(@RequestBody LoginRequestDto requestDto){

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),
                    requestDto.getPassword()
            )
    );

    User user = userRepo.findByEmail(requestDto.getEmail())
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    return jwtUtils.generateToken(user.getEmail());
}

}
