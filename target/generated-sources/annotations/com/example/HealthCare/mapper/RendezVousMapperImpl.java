package com.example.HealthCare.mapper;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.entity.RendezVous;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T12:22:29+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class RendezVousMapperImpl implements RendezVousMapper {

    @Override
    public RendezVousResponseDTO toDto(RendezVous rendezVous) {
        if ( rendezVous == null ) {
            return null;
        }

        RendezVousResponseDTO rendezVousResponseDTO = new RendezVousResponseDTO();

        rendezVousResponseDTO.setPatientId( rendezVousPatientId( rendezVous ) );
        rendezVousResponseDTO.setMedecinId( rendezVousMedecinId( rendezVous ) );
        rendezVousResponseDTO.setId( rendezVous.getId() );
        rendezVousResponseDTO.setDateRendezVous( rendezVous.getDateRendezVous() );
        rendezVousResponseDTO.setStatut( rendezVous.getStatut() );

        return rendezVousResponseDTO;
    }

    @Override
    public List<RendezVousResponseDTO> toDto(List<RendezVous> rendezVousList) {
        if ( rendezVousList == null ) {
            return null;
        }

        List<RendezVousResponseDTO> list = new ArrayList<RendezVousResponseDTO>( rendezVousList.size() );
        for ( RendezVous rendezVous : rendezVousList ) {
            list.add( toDto( rendezVous ) );
        }

        return list;
    }

    @Override
    public RendezVous toEntity(RendezVouRequestDTO rendezVousRequestDTO) {
        if ( rendezVousRequestDTO == null ) {
            return null;
        }

        RendezVous rendezVous = new RendezVous();

        rendezVous.setDateRendezVous( rendezVousRequestDTO.getDateRendezVous() );
        rendezVous.setStatut( rendezVousRequestDTO.getStatut() );

        return rendezVous;
    }

    @Override
    public void update(RendezVouRequestDTO requestDTO, RendezVous rendezVous) {
        if ( requestDTO == null ) {
            return;
        }

        rendezVous.setDateRendezVous( requestDTO.getDateRendezVous() );
        rendezVous.setStatut( requestDTO.getStatut() );
    }

    private Long rendezVousPatientId(RendezVous rendezVous) {
        if ( rendezVous == null ) {
            return null;
        }
        Patient patient = rendezVous.getPatient();
        if ( patient == null ) {
            return null;
        }
        Long id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long rendezVousMedecinId(RendezVous rendezVous) {
        if ( rendezVous == null ) {
            return null;
        }
        Medecin medecin = rendezVous.getMedecin();
        if ( medecin == null ) {
            return null;
        }
        Long id = medecin.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
