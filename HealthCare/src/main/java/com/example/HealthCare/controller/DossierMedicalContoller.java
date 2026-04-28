package com.example.HealthCare.controller;

import com.example.HealthCare.dto.DossierMedicalRequestDto;
import com.example.HealthCare.dto.DossierMedicalResponseDto;
import com.example.HealthCare.service.DossierMedicalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dossier")
@RequiredArgsConstructor
public class DossierMedicalContoller {
    private final DossierMedicalService service;

    @PostMapping
    public DossierMedicalResponseDto createDossier(@Valid @RequestBody DossierMedicalRequestDto requestDto){
        return service.createDossier(requestDto);
    }
}
