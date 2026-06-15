package com.example.HealthCare.service;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.mapper.PatientMapper;
import com.example.HealthCare.repository.PatientRepo;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    @CacheEvict(value = "PATIENT_CACHE", allEntries = true)
    public PatientResponseDTO addPatient(PatientRequestDTO patientDTO) {
        if(patientRepo.existsByEmail(patientDTO.getEmail())){
           throw  new RuntimeException("Email already exist");
        }
        Patient addPAtient= patientMapper.toEntity(patientDTO);
        Patient savedPatient = patientRepo.save(addPAtient);
        return patientMapper.toDto(savedPatient);
    }

    @Transactional
    @CachePut(value = "PATIENT_CACHE",key ="#id")
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
        Patient patient = patientRepo.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        patientMapper.updatePatient(dto, patient);
        return patientMapper.toDto(patient);
    }
    @Transactional
    @CacheEvict(value = "PATIENT_CACHE",key ="#id")
    public void deletePatient(Long id){
        patientRepo.deleteById(id);
    }

    @Transactional
    @Cacheable(value = "PATIENT_CACHE", key = "#page + '-' + #size ")
    public Page<PatientResponseDTO> getAllPatient(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Patient> patients= patientRepo.findAll(pageable);
        return patients.map(patientMapper::toDto);
    }

    @Transactional
    @Cacheable(value = "PATIENT_CACHE",key ="#id")
    public  PatientResponseDTO  getPatientById(Long id){
        System.out.println("Fetching patient from DB...");
        
        Patient patient= patientRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        return patientMapper.toDto(patient);
    }

    @Transactional
    @Cacheable(value = "PATIENT_CACHE", key = "#nom + '-' + #page + '-' + #size")
    public Page<Patient>searchPatient(String nom, int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return patientRepo.findByNomContaining(nom,pageable);
    }




}

