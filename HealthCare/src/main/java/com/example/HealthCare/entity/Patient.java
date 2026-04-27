package com.example.HealthCare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String  telephone;
    private LocalDate dateNaissance;

    @OneToMany(mappedBy = "patient")
    private List<RendezVous> listeRendezVous;

    @OneToOne
    @JoinColumn(name = "dossier_id")
    private DossierMedical dossierMedical;

}
