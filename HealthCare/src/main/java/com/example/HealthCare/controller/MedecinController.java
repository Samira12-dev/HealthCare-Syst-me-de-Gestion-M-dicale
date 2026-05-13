package com.example.HealthCare.controller;

import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.service.MedecinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name ="bearerAuth")
@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {
    private  final MedecinService medecinService;


    @PostMapping
    @Operation(summary = "ajouter medecin")
    public MedecinResponseDTO addMedecin(@Valid  @RequestBody MedecinRequestDTO medecinrequestdto){
        return medecinService.addMedecin(medecinrequestdto);
    }
    @PutMapping("/{id}")
    @Operation(summary = "modifier medecin")
    public MedecinResponseDTO updateMedecin(@PathVariable Long id, @Valid @RequestBody MedecinRequestDTO medecinRequestDTO){
        return medecinService.updaMedecin(id,medecinRequestDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete medecin")
    public void  deleteMedecin(@PathVariable Long id){
        medecinService.deleteMedecin(id);
    }

    @GetMapping
    @Operation(summary = "listerles medecins")
    public List<MedecinResponseDTO>getAllMedecin(){
        return medecinService.getAllMedecin();
    }

}
