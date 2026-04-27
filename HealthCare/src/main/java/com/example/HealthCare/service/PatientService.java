package com.example.HealthCare.service;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.mapper.PatientMapper;
import com.example.HealthCare.repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PatientResponseDTO updatePateint(Long id,PatientRequestDTO patient){
        Patient  findPatient=patientRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        patientMapper.updatePatient(patient,findPatient);
        Patient patientUpdated=patientRepo.save(findPatient);
        return patientMapper.toDto(patientUpdated);
    }
    public void deletePatient(Long id){
        patientRepo.deleteById(id);
    }

    public List<PatientResponseDTO> getAllPatient(){
        List<Patient> patientList =patientRepo.findAll();
        return patientMapper.toDto(patientList);
    }

    public  PatientResponseDTO  getPatientById(Long id){
        Patient patient= patientRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        return patientMapper.toDto(patient);
    }
}

