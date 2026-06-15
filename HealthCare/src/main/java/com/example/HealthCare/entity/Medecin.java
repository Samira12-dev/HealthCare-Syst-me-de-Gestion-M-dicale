package com.example.HealthCare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medecin extends User{

    private String nom;
    private String specialite;
    private String telephone;

    @OneToMany(mappedBy = "medecin")
    private List<RendezVous>rendezVousList;

}
