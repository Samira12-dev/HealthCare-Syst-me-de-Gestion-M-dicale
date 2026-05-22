package com.example.HealthCare.repository;

import com.example.HealthCare.entity.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedecinRepo  extends JpaRepository<Medecin,Long> {
Optional<Medecin>findByEmail(String email);
    Page<Medecin> findBySpecialite(String specialite, Pageable pageable);
}
