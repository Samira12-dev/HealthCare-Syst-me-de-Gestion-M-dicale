package com.example.HealthCare.controller;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    public ResponseEntity<Page<PatientResponseDTO>>getAllPatientss(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10")int size){

        Page<PatientResponseDTO> result=patientService.getAllPatient(page,size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "supprimer patient par id")
    public PatientResponseDTO getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }


    @GetMapping("/search")
    @Operation(summary ="search by nom")
    public ResponseEntity<Page<Patient>>searchByNom(@RequestParam String nom,
                                                    @RequestParam(defaultValue = "0")int page,
                                                    @RequestParam(defaultValue = "5")int size){
        return ResponseEntity.ok(patientService.searchPatient(nom,page,size));
    }

}
