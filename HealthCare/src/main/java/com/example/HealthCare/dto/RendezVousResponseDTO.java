package com.example.HealthCare.dto;

import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.entity.StatutRendezVous;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousResponseDTO {

    private Long id;
    private LocalDateTime dateRendezVous;
    private StatutRendezVous statut;
    private Long patientId;
    private Long medecinId;
}
