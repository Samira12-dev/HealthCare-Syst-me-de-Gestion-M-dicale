package com.example.HealthCare.repository;

import com.example.HealthCare.entity.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedecinRepo  extends JpaRepository<Medecin,Long> {


    @EntityGraph(attributePaths = "rendezVousList")
    Optional<Medecin> findById(Long aLong);

    boolean existsByEmail(String email);
    Page<Medecin> findBySpecialite(String specialite, Pageable pageable);
}
