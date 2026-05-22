package com.example.HealthCare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPatientDto {

//    @NotBlank(message = "Username is required")
//    private String username;
//
//    @NotBlank(message = "Email is required")
//    @Email(message = "Invalid email format")
//    private String email;
//
//    @NotBlank(message = "Password is required")
//    @Size(min = 6,message = "Password must contain at least 6 characters")
//    private String password;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Telephone is required")
    private String telephone;
    @Past(message = "Date de naissance must be in the past")
    private LocalDate dateNaissance;
}