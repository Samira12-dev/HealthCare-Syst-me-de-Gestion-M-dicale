package com.example.HealthCare.repository;

import com.example.HealthCare.entity.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepo  extends JpaRepository<Medecin,Long> {

    boolean existsByEmail(String email);
    Page<Medecin> findBySpecialite(String specialite, Pageable pageable);
}
