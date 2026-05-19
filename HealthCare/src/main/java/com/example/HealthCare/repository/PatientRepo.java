package com.example.HealthCare.repository;

import com.example.HealthCare.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContaining(String nom, Pageable pageable); // بحث مع pagination

    boolean existsByEmail(String email);
}
