package com.example.HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private int telephone;
    private LocalDate dateNaissance;
}
