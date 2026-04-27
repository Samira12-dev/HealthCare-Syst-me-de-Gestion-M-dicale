package com.example.HealthCare.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le nom de specialite estobligatoire")
    private String specialite;
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est invalide")
    private String email;
    @NotBlank(message = "Le telephone est obligatoire")
    private String telephone;
}
