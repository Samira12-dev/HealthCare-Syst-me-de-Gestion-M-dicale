package com.example.HealthCare.mapper;

import com.example.HealthCare.dto.PatientRequestDTO;
import com.example.HealthCare.dto.PatientResponseDTO;
import com.example.HealthCare.entity.Patient;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-27T17:49:49+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Amazon.com Inc.)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Override
    public PatientResponseDTO toDto(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        patientResponseDTO.setId( patient.getId() );
        patientResponseDTO.setNom( patient.getNom() );
        patientResponseDTO.setPrenom( patient.getPrenom() );
        patientResponseDTO.setEmail( patient.getEmail() );
        patientResponseDTO.setTelephone( patient.getTelephone() );
        patientResponseDTO.setDateNaissance( patient.getDateNaissance() );

        return patientResponseDTO;
    }

    @Override
    public List<PatientResponseDTO> toDto(List<Patient> patientList) {
        if ( patientList == null ) {
            return null;
        }

        List<PatientResponseDTO> list = new ArrayList<PatientResponseDTO>( patientList.size() );
        for ( Patient patient : patientList ) {
            list.add( toDto( patient ) );
        }

        return list;
    }

    @Override
    public Patient toEntity(PatientRequestDTO patientRequestDTO) {
        if ( patientRequestDTO == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setNom( patientRequestDTO.getNom() );
        patient.setPrenom( patientRequestDTO.getPrenom() );
        patient.setEmail( patientRequestDTO.getEmail() );
        patient.setTelephone( patientRequestDTO.getTelephone() );
        patient.setDateNaissance( patientRequestDTO.getDateNaissance() );

        return patient;
    }

    @Override
    public void updatePatient(PatientRequestDTO patientRequestDTO, Patient patient) {
        if ( patientRequestDTO == null ) {
            return;
        }

        patient.setNom( patientRequestDTO.getNom() );
        patient.setPrenom( patientRequestDTO.getPrenom() );
        patient.setEmail( patientRequestDTO.getEmail() );
        patient.setTelephone( patientRequestDTO.getTelephone() );
        patient.setDateNaissance( patientRequestDTO.getDateNaissance() );
    }
}
