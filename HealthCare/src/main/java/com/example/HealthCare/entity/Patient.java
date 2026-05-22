package com.example.HealthCare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("PATIENT")
@PrimaryKeyJoinColumn(name = "id")
public class Patient  extends User{

    private String nom;
    private String prenom;
    private String  telephone;
    @Past
    private LocalDate dateNaissance;

    @OneToMany(mappedBy = "patient")
    private List<RendezVous> listeRendezVous;

    @OneToOne(mappedBy = "patient")
    private DossierMedical dossierMedical;

}
