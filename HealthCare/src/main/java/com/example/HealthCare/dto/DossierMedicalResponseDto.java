package com.example.HealthCare.dto;

import com.example.HealthCare.entity.Patient;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DossierMedicalResponseDto {

    private Long id;
    private String diagnostic;
    private String observation;
    private LocalDate dateCreation;



}
