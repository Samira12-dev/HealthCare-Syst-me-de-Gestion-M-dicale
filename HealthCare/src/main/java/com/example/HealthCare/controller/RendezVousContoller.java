package com.example.HealthCare.controller;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.service.RendezVousService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rendez_vous")
@RequiredArgsConstructor
public class RendezVousContoller {
    private final RendezVousService rendezVousService;

    @PostMapping
    public RendezVousResponseDTO createRendezVous(@Valid @RequestBody RendezVouRequestDTO requestDTO){
        return  rendezVousService.createRendezVous(requestDTO);
    }
    @PutMapping("/{id}")
    public RendezVousResponseDTO updaterendezVous(@PathVariable Long id, @Valid @RequestBody RendezVouRequestDTO requestDTO){
        return rendezVousService.updateRendezVous(id, requestDTO);
    }
    @PutMapping("{id}/annuler")
    public RendezVousResponseDTO annulerRendezVous(@PathVariable Long id ){
        return rendezVousService.annulerRendezVous(id);
    }
    @GetMapping
    public List<RendezVousResponseDTO> getAllRendezVous(){
        return rendezVousService.getAllRendezVous();
    }

    @GetMapping("/patient/{patientId}")
    public List<RendezVousResponseDTO> getRendezVousByPatient(@PathVariable Long patientId){
        return rendezVousService.rechercheRendezVousPatient(patientId);
    }
    @GetMapping("/medecin/{medecinId}")
    public List<RendezVousResponseDTO> getRendezVousByMedecin(@PathVariable Long medecinId){
        return rendezVousService.rechercheRendezVousMedecin(medecinId);
    }
}

