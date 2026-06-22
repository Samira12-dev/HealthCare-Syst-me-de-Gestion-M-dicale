package com.example.HealthCare.mapper;

import com.example.HealthCare.dto.DossierMedicalRequestDto;
import com.example.HealthCare.dto.DossierMedicalResponseDto;
import com.example.HealthCare.entity.DossierMedical;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-22T16:10:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class DossierMedicalMapperImpl implements DossierMedicalMapper {

    @Override
    public DossierMedicalResponseDto toDto(DossierMedical dossierMedical) {
        if ( dossierMedical == null ) {
            return null;
        }

        DossierMedicalResponseDto dossierMedicalResponseDto = new DossierMedicalResponseDto();

        dossierMedicalResponseDto.setId( dossierMedical.getId() );
        dossierMedicalResponseDto.setDiagnostic( dossierMedical.getDiagnostic() );
        dossierMedicalResponseDto.setObservation( dossierMedical.getObservation() );
        dossierMedicalResponseDto.setDateCreation( dossierMedical.getDateCreation() );

        return dossierMedicalResponseDto;
    }

    @Override
    public DossierMedical toEntity(DossierMedicalRequestDto dossierMedicalRequestDto) {
        if ( dossierMedicalRequestDto == null ) {
            return null;
        }

        DossierMedical dossierMedical = new DossierMedical();

        dossierMedical.setDiagnostic( dossierMedicalRequestDto.getDiagnostic() );
        dossierMedical.setObservation( dossierMedicalRequestDto.getObservation() );
        dossierMedical.setDateCreation( dossierMedicalRequestDto.getDateCreation() );

        return dossierMedical;
    }
}
