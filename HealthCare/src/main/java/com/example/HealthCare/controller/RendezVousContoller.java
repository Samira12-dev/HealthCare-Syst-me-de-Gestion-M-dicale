package com.example.HealthCare.controller;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.service.RendezVousService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/rendez_vous")
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
    public List<RendezVousResponseDTO> getAllRendezVous(){
        return rendezVousService.getAllRendezVous();
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



}

