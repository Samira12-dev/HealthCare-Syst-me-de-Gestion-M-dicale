package com.example.HealthCare.controller;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patients")
@RequiredArgsConstructor
public class PatientController {
    private  final PatientService patientService;


    @PostMapping
    public PatientResponseDTO addPatient(@Valid @RequestBody PatientRequestDTO patientdto){
        return  patientService.addPatient(patientdto);
    }
    @PutMapping("/id")
    public PatientResponseDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientdto){
        return patientService.updatePateint(id, patientdto);
    }

    @DeleteMapping("/id")
    public void deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
    }
    @GetMapping
    public List<PatientResponseDTO>getAllPatientss(){
        return  patientService.getAllPatient();
    }
    @GetMapping("/id")
    public PatientResponseDTO getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

}
