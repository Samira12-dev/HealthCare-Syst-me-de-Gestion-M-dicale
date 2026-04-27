package com.example.HealthCare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedecinResponseDTO {

    private Long id;
    private String nom;
    private String specialite;
    private String email;
    private String telephone;
}
