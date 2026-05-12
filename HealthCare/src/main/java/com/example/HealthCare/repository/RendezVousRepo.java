package com.example.HealthCare.repository;

import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RendezVousRepo extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByMedecinId(Long medecinId);

    List<RendezVous> findByStatut(@Param("statut")String statut);

}
