package com.example.HealthCare.mapper;

import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.entity.Medecin;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-21T17:58:14+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class MedecinMapperImpl implements MedecinMapper {

    @Override
    public MedecinResponseDTO toDTO(Medecin medecin) {
        if ( medecin == null ) {
            return null;
        }

        MedecinResponseDTO medecinResponseDTO = new MedecinResponseDTO();

        medecinResponseDTO.setId( medecin.getId() );
        medecinResponseDTO.setNom( medecin.getNom() );
        medecinResponseDTO.setSpecialite( medecin.getSpecialite() );
        medecinResponseDTO.setEmail( medecin.getEmail() );
        medecinResponseDTO.setTelephone( medecin.getTelephone() );

        return medecinResponseDTO;
    }

    @Override
    public List<MedecinResponseDTO> toDTO(List<Medecin> medecins) {
        if ( medecins == null ) {
            return null;
        }

        List<MedecinResponseDTO> list = new ArrayList<MedecinResponseDTO>( medecins.size() );
        for ( Medecin medecin : medecins ) {
            list.add( toDTO( medecin ) );
        }

        return list;
    }

    @Override
    public Medecin toEntity(MedecinRequestDTO medecinRequestDTO) {
        if ( medecinRequestDTO == null ) {
            return null;
        }

        Medecin medecin = new Medecin();

        medecin.setEmail( medecinRequestDTO.getEmail() );
        medecin.setNom( medecinRequestDTO.getNom() );
        medecin.setSpecialite( medecinRequestDTO.getSpecialite() );
        medecin.setTelephone( medecinRequestDTO.getTelephone() );

        return medecin;
    }

    @Override
    public void update(MedecinRequestDTO medecinRequestDTO, Medecin med) {
        if ( medecinRequestDTO == null ) {
            return;
        }

        med.setEmail( medecinRequestDTO.getEmail() );
        med.setNom( medecinRequestDTO.getNom() );
        med.setSpecialite( medecinRequestDTO.getSpecialite() );
        med.setTelephone( medecinRequestDTO.getTelephone() );
    }
}
