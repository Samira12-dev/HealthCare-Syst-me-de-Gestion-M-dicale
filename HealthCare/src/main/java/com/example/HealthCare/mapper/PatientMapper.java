package com.example.HealthCare.mapper;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientResponseDTO toDto(Patient patient);
    List<PatientResponseDTO> toDto(List<Patient>patientList);
    Patient toEntity (PatientRequestDTO patientRequestDTO);

    void updatePatient(PatientRequestDTO patientRequestDTO, @MappingTarget Patient patient);

}
