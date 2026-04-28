package com.example.HealthCare.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierMedicalRequestDto {

    private String diagnostic;
    private String observation;
    @NotNull(message = "La date creation  est obligatoire")
    @FutureOrPresent(message = "La date doit être présente ou future")
    private LocalDate dateCreation;
    @NotNull(message = "L'id du patient est obligatoire")
    private Long patientId;
}
