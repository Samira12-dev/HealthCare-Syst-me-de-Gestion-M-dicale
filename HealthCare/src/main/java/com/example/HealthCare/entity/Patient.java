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
public class Patient extends User{
    private String nom;
    private String prenom;
    private String  telephone;
    private LocalDate dateNaissance;

    @OneToMany(mappedBy = "patient")
    private List<RendezVous> listeRendezVous;

    @OneToOne(mappedBy = "patient")
    private DossierMedical dossierMedical;

}
