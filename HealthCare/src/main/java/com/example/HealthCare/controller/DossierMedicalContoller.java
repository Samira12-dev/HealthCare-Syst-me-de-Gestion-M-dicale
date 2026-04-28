package com.example.HealthCare.controller;

import com.example.HealthCare.dto.DossierMedicalRequestDto;
import com.example.HealthCare.dto.DossierMedicalResponseDto;
import com.example.HealthCare.service.DossierMedicalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dossier")
@RequiredArgsConstructor
public class DossierMedicalContoller {
    private final DossierMedicalService dossierMedicalService;

    @PostMapping
    @Operation(summary = "Creation dossier medical")
    public DossierMedicalResponseDto createDossier(@Valid @RequestBody DossierMedicalRequestDto requestDto){
        return dossierMedicalService.createDossier(requestDto);
    }
    @PutMapping("/api/dossier/{id}/diagnostic")
    @Operation(summary = "Ajouter diagnostic")
    public  DossierMedicalResponseDto addDiagnostic(@Valid @RequestBody String diagnostic){
        return dossierMedicalService.ajouterDiagnostic(diagnostic);
    }

    @PutMapping("/api/dossier/{id}/observation")
    @Operation(summary = "Ajouter observation")
    public  DossierMedicalResponseDto addObservation(@Valid @RequestBody String observation){
        return dossierMedicalService.ajouterObeservation(observation);
    }
    @GetMapping("/dossier/{id}")
    @Operation(summary = "Consulter dossier")
    public DossierMedicalResponseDto getDossierMedicalById(@PathVariable Long id , @Valid @RequestBody DossierMedicalRequestDto requestDto){
        return dossierMedicalService.findDossierMedicalById(id);
    }
}
