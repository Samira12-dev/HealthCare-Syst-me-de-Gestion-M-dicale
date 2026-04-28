package com.example.HealthCare.mapper;

import com.example.HealthCare.dto.DossierMedicalRequestDto;
import com.example.HealthCare.dto.DossierMedicalResponseDto;
import com.example.HealthCare.entity.DossierMedical;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DossierMedicalMapper{
DossierMedicalResponseDto toDto(DossierMedical dossierMedical);

DossierMedical toEntity(DossierMedicalRequestDto dossierMedicalRequestDto);

}
