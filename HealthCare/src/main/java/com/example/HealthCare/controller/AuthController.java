package com.example.HealthCare.controller;

import com.example.HealthCare.config.JwtUtils;
import com.example.HealthCare.dto.LoginRequestDto;
import com.example.HealthCare.dto.RegisterMedecinDto;
import com.example.HealthCare.dto.RegisterPatientDto;
import com.example.HealthCare.dto.RegisterRequestDto;
import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.entity.Role;
import com.example.HealthCare.entity.User;
import com.example.HealthCare.repository.UserRepo;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto dto) {



        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User savedUser;

        // 2. التحقق حسب الدور المتواجد في الـ JSON
        if (dto.getRole() == Role.PATIENT) {
            if (dto.getPatient() == null) {
                return ResponseEntity.badRequest().body("Patient details are required for PATIENT role");
            }

            Patient patient = new Patient();
            // الحقول المشتركة من الـ User
            patient.setUsername(dto.getUsername());
            patient.setEmail(dto.getEmail());
            patient.setPassword(passwordEncoder.encode(dto.getPassword()));
            patient.setRole(Role.PATIENT);

            // الحقول الخاصة بالمريض
            patient.setNom(dto.getPatient().getNom());
            patient.setPrenom(dto.getPatient().getPrenom());
            patient.setTelephone(dto.getPatient().getTelephone());
            patient.setDateNaissance(dto.getPatient().getDateNaissance());

            savedUser = userRepo.save(patient);

        } else if (dto.getRole() == Role.MEDECIN) {
            if (dto.getMedecin() == null) {
                return ResponseEntity.badRequest().body("Medecin details are required for MEDECIN role");
            }

            Medecin medecin = new Medecin();
            // الحقول المشتركة من الـ User
            medecin.setUsername(dto.getUsername());
            medecin.setEmail(dto.getEmail());
            medecin.setPassword(passwordEncoder.encode(dto.getPassword()));
            medecin.setRole(Role.MEDECIN);

            // الحقول الخاصة بالطبيب
            medecin.setNom(dto.getMedecin().getNom());
            medecin.setTelephone(dto.getMedecin().getTelephone());
            medecin.setSpecialite(dto.getMedecin().getSpecialite());

            savedUser = userRepo.save(medecin);

        } else {
            return ResponseEntity.badRequest().body("Invalid role specified");
        }

        // 3. توليد الـ Token وإرجاع النتيجة
        String token = jwtUtils.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer",
                "email", savedUser.getEmail(),
                "role", savedUser.getRole()
        ));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );

            if (auth.isAuthenticated()) {

                User user = userRepo.findByEmail(dto.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                String token = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("type", "Bearer");
                response.put("email", user.getEmail());
                response.put("role", user.getRole());
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login");
        }
    }


}