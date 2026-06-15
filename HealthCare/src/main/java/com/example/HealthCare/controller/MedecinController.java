package com.example.HealthCare.controller;

import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.service.MedecinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Page<MedecinResponseDTO>>getAllMedecin(@RequestParam (defaultValue = "0")int page,
                                                                 @RequestParam(defaultValue = "10")int size,
                                                                 @RequestParam(defaultValue = "specialite")String sortBy){


        Page<MedecinResponseDTO> responseDTOS= medecinService.getAllMedecin(page, size, sortBy);
        return ResponseEntity.ok(responseDTOS);

    }
    @GetMapping("/search")
    @Operation(summary = "search by speciality")
    public  Page<MedecinResponseDTO>searchBySpeciality(@RequestParam String speciality,
                                                       @RequestParam (defaultValue = "0")int page,
                                                       @RequestParam(defaultValue = "5")int size){
        return medecinService.searchDoctor(speciality,page,size);
    }

}
