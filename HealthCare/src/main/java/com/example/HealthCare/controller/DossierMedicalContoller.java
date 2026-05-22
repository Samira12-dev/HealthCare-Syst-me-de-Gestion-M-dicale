package com.example.HealthCare.controller;

import com.example.HealthCare.dto.DossierMedicalRequestDto;
import com.example.HealthCare.dto.DossierMedicalResponseDto;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.service.DossierMedicalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name ="bearerAuth")
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
    @PutMapping("/{id}/diagnostic")
    @Operation(summary = "Ajouter diagnostic")
    public  DossierMedicalResponseDto addDiagnostic(@PathVariable Long id,@Valid @RequestBody String diagnostic){

        return dossierMedicalService.ajouterDiagnostic(id,diagnostic);
    }

    @PutMapping("/{id}/observation")
    @Operation(summary = "Ajouter observation")
    public  DossierMedicalResponseDto addObservation(@PathVariable Long id,@Valid @RequestBody String observation){
        return dossierMedicalService.ajouterObeservation(id,observation);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Consulter dossier")
    public DossierMedicalResponseDto getDossierMedicalById(@PathVariable Long id ){
        return dossierMedicalService.findDossierMedicalById(id);
    }
    @GetMapping
    @Operation(summary = "Consulter les dossiers médicaux ")
    public Page<DossierMedicalResponseDto> getAllDossier(@RequestParam(defaultValue = "0")int page,
                                                        @RequestParam(defaultValue = "5")int size,
                                                        @RequestParam(defaultValue = "id")String sortBy) {
        return dossierMedicalService.getAllDossier(page, size, sortBy);
    }

    @GetMapping("/me")
    @Operation(summary ="Consulter mon dossier médical" )
    public ResponseEntity<DossierMedicalResponseDto> getMyDossier(){
        return  ResponseEntity.ok(dossierMedicalService.getMyDossier());
    }


}
