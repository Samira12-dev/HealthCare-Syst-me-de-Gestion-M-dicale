package com.example.HealthCare.service;

import com.example.HealthCare.dto.DossierMedicalRequestDto;
import com.example.HealthCare.dto.DossierMedicalResponseDto;
import com.example.HealthCare.entity.DossierMedical;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.mapper.DossierMedicalMapper;
import com.example.HealthCare.repository.DossierMedicalRepo;
import com.example.HealthCare.repository.PatientRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DossierMedicalService {

    private final DossierMedicalMapper dossierMedicalMapper;
    private final DossierMedicalRepo dossierMedicalRepo;
    private final PatientRepo patientRepo;

    public DossierMedicalService(DossierMedicalMapper dossierMedicalMapper, DossierMedicalRepo dossierMedicalRepo, PatientRepo patientRepo) {
        this.dossierMedicalMapper=dossierMedicalMapper;
        this.dossierMedicalRepo=dossierMedicalRepo;
        this.patientRepo=patientRepo;
    }


    @Transactional
    public DossierMedicalResponseDto createDossier(DossierMedicalRequestDto dossierRequestDto) {

        DossierMedical dossierMedical=dossierMedicalMapper.toEntity(dossierRequestDto);
        Patient patient = patientRepo.findById(dossierRequestDto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        dossierMedical.setPatient(patient);
        dossierMedicalRepo.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    @Transactional
    public DossierMedicalResponseDto ajouterDiagnostic(String diagnostic) {
        DossierMedical addingdiagnostic= dossierMedicalRepo.findByDiagnostic(diagnostic);
        return  dossierMedicalMapper.toDto(addingdiagnostic);
    }

    @Transactional
    public DossierMedicalResponseDto ajouterObeservation(String observation) {
        DossierMedical addObservation= dossierMedicalRepo.findByObservation(observation);
        return dossierMedicalMapper.toDto(addObservation);
    }

    @Transactional
    public  DossierMedicalResponseDto findDossierMedicalById(Long id){
        DossierMedical dossier = dossierMedicalRepo.findById(id).orElseThrow(()->new RuntimeException("Dossier not found"));
        return  dossierMedicalMapper.toDto(dossier);
    }
}
