package com.example.HealthCare.controller;

import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.service.MedecinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medecins")
@RequiredArgsConstructor
public class MedecinController {
    private  final MedecinService medecinService;


    @PostMapping
    public MedecinResponseDTO addMedecin(@Valid  @RequestBody MedecinRequestDTO medecinrequestdto){
        return medecinService.addMedecin(medecinrequestdto);
    }
    @PutMapping("/{id}")
    public MedecinResponseDTO updateMedecin(@PathVariable Long id, @Valid @RequestBody MedecinRequestDTO medecinRequestDTO){
        return medecinService.updaMedecin(id,medecinRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void  deleteMedecin(@PathVariable Long id){
        medecinService.deleteMedecin(id);
    }

    @GetMapping
    public List<MedecinResponseDTO>getAllMedecin(){
        return medecinService.getAllMedecin();
    }

}
