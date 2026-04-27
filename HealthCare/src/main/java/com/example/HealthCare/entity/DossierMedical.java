package com.example.HealthCare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnostic;
    private String observation;
    private LocalDate dateCreation;

    @OneToOne
    @JoinColumn(name = "patient_id", unique = true)
    private Patient patient;
}