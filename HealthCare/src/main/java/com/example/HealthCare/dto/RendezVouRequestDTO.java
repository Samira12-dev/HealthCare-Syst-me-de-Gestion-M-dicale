package com.example.HealthCare.dto;


import com.example.HealthCare.entity.StatutRendezVous;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RendezVouRequestDTO {
    @NotNull(message = "La date de rendez_vous est obligatoire")
    private LocalDateTime dateRendezVous;

    @NotNull(message = "Le statut est obligatoire")
    private StatutRendezVous statut;

    @NotNull(message = "L'id du patient est obligatoire")
    private Long patientId;

    @NotNull(message = "L'id du médecin est obligatoire")
    private Long medecinId;
}
