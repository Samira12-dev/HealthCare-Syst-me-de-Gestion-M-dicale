package com.example.HealthCare.service;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.mapper.PatientMapper;
import com.example.HealthCare.repository.PatientRepo;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final PatientRepo  patientRepo;
    private final PatientMapper patientMapper;
    public  PatientService(PatientRepo patientRepo, PatientMapper patientMapper) {
        this.patientRepo = patientRepo;
        this.patientMapper = patientMapper;
    }

    public PatientResponseDTO addPatient(PatientRequestDTO patientDTO) {
        if(patientRepo.existsByEmail(patientDTO.getEmail())){
           throw  new RuntimeException("Email already exist");
        }
        Patient addPAtient= patientMapper.toEntity(patientDTO);
        Patient savedPatient = patientRepo.save(addPAtient);
        return patientMapper.toDto(savedPatient);
    }

}
