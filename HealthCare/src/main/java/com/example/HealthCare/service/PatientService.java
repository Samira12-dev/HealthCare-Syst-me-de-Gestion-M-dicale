package com.example.HealthCare.service;

import com.example.HealthCare.config.SecurityConfig;
import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.mapper.PatientMapper;
import com.example.HealthCare.repository.PatientRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public PatientResponseDTO addPatient(PatientRequestDTO patientDTO) {
        if(patientRepo.findByEmail(patientDTO.getEmail()).isPresent()){
           throw  new RuntimeException("Email already exist");
        }
        Patient addPAtient= patientMapper.toEntity(patientDTO);
        Patient savedPatient = patientRepo.save(addPAtient);
        return patientMapper.toDto(savedPatient);
    }

    @Transactional
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
        Patient patient = patientRepo.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        patientMapper.updatePatient(dto, patient);
        return patientMapper.toDto(patient);
    }
    @Transactional
    public void deletePatient(Long id){
        patientRepo.deleteById(id);
    }

    @Transactional
    public Page<PatientResponseDTO> getAllPatient(int page,int size,String sortby){
        Pageable pageable= PageRequest.of(page,size, Sort.by(sortby).ascending());
        Page<Patient> patients= patientRepo.findAll(pageable);
        return patients.map(patientMapper::toDto);
    }

    @Transactional
    public  PatientResponseDTO  getPatientById(Long id){
        Patient patient= patientRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        return patientMapper.toDto(patient);
    }

    @Transactional
   public Page<Patient>searchPatient(String nom, int page, int size){
        Pageable pageable=PageRequest.of(page,size);
        return patientRepo.findByNomContaining(nom,pageable);
    }


    @Transactional
    public PatientResponseDTO getMyProfile(){
        String email= SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Patient patient= patientRepo.findByEmail(email).orElseThrow(()->new RuntimeException( "Not found"));
        return patientMapper.toDto(patient);
    }

    @Transactional
    public  PatientResponseDTO updateMyProfile(PatientRequestDTO dto){

        String email= SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Patient patient =patientRepo.findByEmail(email).orElseThrow(()->new RuntimeException("not found"));
        patient.setNom(dto.getNom());
        patient.setPrenom(dto.getPrenom());
        patient.setTelephone(dto.getTelephone());
        return patientMapper.toDto(patient);
    }
}

