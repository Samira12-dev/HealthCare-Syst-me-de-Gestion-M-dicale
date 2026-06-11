package com.example.HealthCare.repository;

import com.example.HealthCare.entity.DossierMedical;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DossierMedicalRepo extends JpaRepository<DossierMedical, Long> {
    @EntityGraph(attributePaths = "patient")
    Optional<DossierMedical> findById(Long id);
}

