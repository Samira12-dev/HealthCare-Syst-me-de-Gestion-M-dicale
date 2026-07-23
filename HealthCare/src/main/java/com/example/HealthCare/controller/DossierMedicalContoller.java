package com.example.HealthCare.controller;

import com.example.HealthCare.dto.*;
import com.example.HealthCare.entity.DossierMedical;
import com.example.HealthCare.service.DossierMedicalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<PageResponseDTO<DossierMedicalResponseDto>> getAllDossier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy){

        return ResponseEntity.ok(
                dossierMedicalService.getAllDossier(page,size,sortBy)
        );
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<String>  downloadPDF(@PathVariable Long id)throws Exception{
       String pdf= dossierMedicalService.telechargePDF(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attechment ; file=dossier_medical.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
