package com.example.HealthCare.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MEDECIN")
@PrimaryKeyJoinColumn(name = "id")
public class Medecin extends User{
    private String nom;
    private String specialite;
    private String telephone;
    @OneToMany(mappedBy = "medecin")
    private List<RendezVous>rendezVousList;
}
