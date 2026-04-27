package com.example.HealthCare.repository;

import com.example.HealthCare.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,Long> {

    boolean existsByEmail(String email);
}
