package com.example.HealthCare.controller;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.StatutRendezVous;
import com.example.HealthCare.service.RendezVousService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@SecurityRequirement(name ="bearerAuth")
@RestController
@RequestMapping("/api/rendez_vous")
@RequiredArgsConstructor
public class RendezVousContoller {
    private final RendezVousService rendezVousService;

    @PostMapping
    @Operation(summary = "creation rendez_vous")
    public RendezVousResponseDTO createRendezVous(@Valid @RequestBody RendezVouRequestDTO requestDTO){
        return  rendezVousService.createRendezVous(requestDTO);
    }


    @PutMapping("/{id}")
    @Operation(summary = "modifier rendez_vous")
    public RendezVousResponseDTO updaterendezVous(@PathVariable Long id, @Valid @RequestBody RendezVouRequestDTO requestDTO){
        return rendezVousService.updateRendezVous(id, requestDTO);
    }

    @PutMapping("{id}/annuler")
    @Operation(summary = "annuler rendez_vous")
    public RendezVousResponseDTO annulerRendezVous(@PathVariable Long id ){
        return rendezVousService.annulerRendezVous(id);
    }
    @GetMapping
    @Operation(summary = "lister les rendez_vous")
    public Page<RendezVousResponseDTO> getAllRendezVous(@RequestParam(defaultValue = "0")int page,
                                                     @RequestParam(defaultValue = "5")int size,
                                                        @RequestParam(defaultValue = "dateRendezVous")String sortBy){
        return rendezVousService.getAllRendezVous(page,size,sortBy);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "recherche rendez_vous par patient")
    public List<RendezVousResponseDTO> getRendezVousByPatient(@PathVariable Long patientId){
        return rendezVousService.rechercheRendezVousPatient(patientId);
    }
    @GetMapping("/medecin/{medecinId}")
    @Operation(summary = "rechercher rendez_vous par medecin")
    public List<RendezVousResponseDTO> getRendezVousByMedecin(@PathVariable Long medecinId){
        return rendezVousService.rechercheRendezVousMedecin(medecinId);
    }

    @GetMapping("/search")
    public Page<RendezVousResponseDTO> searchByStatus(
            @RequestParam StatutRendezVous status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return rendezVousService.searchByStatus(status, page, size);
    }

    @GetMapping("/me")
    @Operation(summary = "Consulter mon rendez-vous")
    public ResponseEntity<List<RendezVousResponseDTO>> getMyRendezVous(){
        return ResponseEntity.ok(rendezVousService.getMyRendezVous());
    }
    @GetMapping("/medecin/me")
    @Operation(summary = "Consulter ses rendez-vous pour medecin")
    public ResponseEntity<List<RendezVousResponseDTO>> getMedecinRendezVous() {
        return ResponseEntity.ok(
                rendezVousService.getMyRendezVousMedecin()
        );
    }
}

