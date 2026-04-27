package com.example.HealthCare.mapper;


import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.entity.Medecin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedecinMapper {

        MedecinResponseDTO toDTO (Medecin medecin);
       List<MedecinResponseDTO> toDTO (List<Medecin> medecins);
       Medecin toEntity (MedecinRequestDTO medecinRequestDTO);

       void update(MedecinRequestDTO medecinRequestDTO, @MappingTarget Medecin med);

}
