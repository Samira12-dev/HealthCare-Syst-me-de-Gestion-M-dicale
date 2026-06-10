package com.example.HealthCare.controller;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.StatutRendezVous;
import com.example.HealthCare.service.RendezVousService;
import com.itextpdf.text.DocumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Status;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@SecurityRequirement(name ="bearerAuth")
@RestController
@RequestMapping("api/rendez_vous")
@RequiredArgsConstructor
public class RendezVousContoller {
    private final RendezVousService rendezVousService;

    @PostMapping
    @Operation(summary = "creation rendez_vous")
    public RendezVousResponseDTO createRendezVous(@Valid @RequestBody RendezVouRequestDTO requestDTO) {
        return rendezVousService.createRendezVous(requestDTO);
    }


    @PutMapping("/{id}")
    @Operation(summary = "modifier rendez_vous")
    public RendezVousResponseDTO updaterendezVous(@PathVariable Long id, @Valid @RequestBody RendezVouRequestDTO requestDTO) {
        return rendezVousService.updateRendezVous(id, requestDTO);
    }

    @PutMapping("{id}/annuler")
    @Operation(summary = "annuler rendez_vous")
    public RendezVousResponseDTO annulerRendezVous(@PathVariable Long id) {
        return rendezVousService.annulerRendezVous(id);
    }

    @GetMapping
    @Operation(summary = "lister les rendez_vous")
    public Page<RendezVousResponseDTO> getAllRendezVous(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int size,
                                                        @RequestParam(defaultValue = "dateRendezVous") String sortBy) {
        return rendezVousService.getAllRendezVous(page, size, sortBy);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "recherche rendez_vous par patient")
    public List<RendezVousResponseDTO> getRendezVousByPatient(@PathVariable Long patientId) {
        return rendezVousService.rechercheRendezVousPatient(patientId);
    }

    @GetMapping("/medecin/{medecinId}")
    @Operation(summary = "rechercher rendez_vous par medecin")
    public List<RendezVousResponseDTO> getRendezVousByMedecin(@PathVariable Long medecinId) {
        return rendezVousService.rechercheRendezVousMedecin(medecinId);
    }

    @GetMapping("/search")
    @Operation(summary = "recherch rendez_vous par statut")
    public Page<RendezVousResponseDTO> searchByStatus(
            @RequestParam StatutRendezVous status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return rendezVousService.searchByStatus(status, page, size);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @GetMapping("/tele")
    public ResponseEntity<Page<RendezVousResponseDTO>> findByDateRendezVous(@RequestParam LocalDate datee,
                                                                            @RequestParam(defaultValue = "0")int page,
                                                                            @RequestParam(defaultValue = "10")int size){
        return  ResponseEntity.ok(rendezVousService.findByDateRendezVous(datee,page,size));
    }
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @GetMapping("/{id}/pdf")
    public ResponseEntity<String> downloadPDF(@PathVariable Long id) throws DocumentException, FileNotFoundException {
        return ResponseEntity.ok(rendezVousService.generateRendezVousPDF(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN')")
    @GetMapping("/{id}/rapport")
    public ResponseEntity<String> downloadRapportSimple(@PathVariable Long id)throws  DocumentException,FileNotFoundException{
        return ResponseEntity.ok(rendezVousService.generatrSimpleRapport(id));

    }
}

