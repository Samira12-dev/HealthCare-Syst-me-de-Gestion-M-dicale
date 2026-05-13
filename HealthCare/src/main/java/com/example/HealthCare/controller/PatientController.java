package com.example.HealthCare.controller;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name ="bearerAuth")
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private  final PatientService patientService;


    @PostMapping
    @Operation(summary = "ajouter patient")
    public PatientResponseDTO addPatient(@Valid @RequestBody PatientRequestDTO patientdto){
        return  patientService.addPatient(patientdto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "update patient")
    public PatientResponseDTO updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDTO patientdto){
        return patientService.updatePatient(id, patientdto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete patient")
    public void deletePatient(@PathVariable Long id){
        patientService.deletePatient(id);
    }

    @GetMapping
    @Operation(summary = "lister les patients")
    public List<PatientResponseDTO>getAllPatientss(){
        return  patientService.getAllPatient();
    }

    @GetMapping("/{id}")
    @Operation(summary = "supprimer patient par id")
    public PatientResponseDTO getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

}
