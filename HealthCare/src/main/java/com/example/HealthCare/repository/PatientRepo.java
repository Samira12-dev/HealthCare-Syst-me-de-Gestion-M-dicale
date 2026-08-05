package com.example.HealthCare.repository;

import com.example.HealthCare.entity.DossierMedical;
import com.example.HealthCare.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepo extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContaining(String nom, Pageable pageable);

    boolean existsByEmail(String email);

   @EntityGraph(attributePaths = "listeRendezVous")
    Optional<Patient> findById(Long id);
}
