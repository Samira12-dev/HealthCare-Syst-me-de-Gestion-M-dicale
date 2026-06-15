package com.example.HealthCare.service;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.*;
import com.example.HealthCare.repository.MedecinRepo;
import com.example.HealthCare.repository.PatientRepo;
import com.example.HealthCare.repository.RendezVousRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class RendezVousServiceTest {

    @Autowired
    RendezVousService service;

    @Autowired
    RendezVousRepo rendezVousRepo;

    @Autowired
    PatientRepo patientRepo;

    @Autowired
    MedecinRepo medecinRepo;

    private Patient patientTest;
    private Medecin medecinTest;
    private RendezVousResponseDTO rendezVousCreated;

    @BeforeEach
    void setup() {

        Patient patient = new Patient();
        patient.setUsername("samira");
        patient.setEmail(UUID.randomUUID() + "@gmail.com");
        patient.setPassword("123456");
        patient.setRole(Role.PATIENT);
        patient.setNom("samira");
        patient.setPrenom("El boussidi");
        patient.setTelephone("98765432");

        patientTest = patientRepo.save(patient);

        Medecin medecin = new Medecin();
        medecin.setUsername("ismail");
        medecin.setEmail(UUID.randomUUID() + "@gmail.com");
        medecin.setPassword("123456");
        medecin.setRole(Role.MEDECIN);
        medecin.setNom("Ismail");
        medecin.setSpecialite("cardiology");
        medecin.setTelephone("98765432");

        medecinTest = medecinRepo.save(medecin);

        RendezVouRequestDTO dto = new RendezVouRequestDTO();
        dto.setDateRendezVous(LocalDateTime.now().plusDays(1));
        dto.setPatientId(patientTest.getId());
        dto.setMedecinId(medecinTest.getId());

        rendezVousCreated = service.createRendezVous(dto);
    }
    @Test
    void ajouterRendezVousTest() {

        RendezVouRequestDTO requestDTO = new RendezVouRequestDTO();
        requestDTO.setPatientId(patientTest.getId());
        requestDTO.setMedecinId(medecinTest.getId());

        RendezVousResponseDTO response = service.createRendezVous(requestDTO);

        assertNotNull(response);
        assertEquals(patientTest.getId(), response.getPatientId());
        assertEquals(medecinTest.getId(), response.getMedecinId());
        assertEquals(StatutRendezVous.PLANIFIE, response.getStatut());
    }

    @Test
    void modifierRendezVousTest() {

        RendezVouRequestDTO updated = new RendezVouRequestDTO();
        updated.setPatientId(patientTest.getId());
        updated.setMedecinId(medecinTest.getId());
        updated.setStatut(StatutRendezVous.CONFIRME);

        RendezVousResponseDTO responseDT =
                service.updateRendezVous(rendezVousCreated.getId(), updated);

        assertNotNull(responseDT);
        assertEquals(StatutRendezVous.CONFIRME, responseDT.getStatut());
    }

    @Test
    void annulerRendez_vousTest() {

        RendezVousResponseDTO responseDTO =
                service.annulerRendezVous(rendezVousCreated.getId());

        assertNotNull(responseDTO);
        assertEquals(StatutRendezVous.ANNULE, responseDTO.getStatut());
    }

    @Test
    void rechercheRendezVousPatientTest() {

        RendezVouRequestDTO dto1 = new RendezVouRequestDTO();
        dto1.setDateRendezVous(LocalDateTime.now().plusDays(1));
        dto1.setPatientId(patientTest.getId());
        dto1.setMedecinId(medecinTest.getId());

        service.createRendezVous(dto1);

        RendezVouRequestDTO dto2 = new RendezVouRequestDTO();
        dto2.setDateRendezVous(LocalDateTime.now().plusDays(2));
        dto2.setPatientId(patientTest.getId());
        dto2.setMedecinId(medecinTest.getId());

        service.createRendezVous(dto2);

        List<RendezVousResponseDTO> result =
                service.rechercheRendezVousPatient(patientTest.getId());

        assertNotNull(result);
        assertTrue(result.size() >= 2);
    }
    @Test
    void rechercheRendezVousMedecinTest() {

        RendezVouRequestDTO dto1 = new RendezVouRequestDTO();
        dto1.setDateRendezVous(LocalDateTime.now().plusDays(1));
        dto1.setPatientId(patientTest.getId());
        dto1.setMedecinId(medecinTest.getId());

        service.createRendezVous(dto1);

        RendezVouRequestDTO dto2 = new RendezVouRequestDTO();
        dto2.setDateRendezVous(LocalDateTime.now().plusDays(2));
        dto2.setPatientId(patientTest.getId());
        dto2.setMedecinId(medecinTest.getId());

        service.createRendezVous(dto2);

        List<RendezVousResponseDTO> result =
                service.rechercheRendezVousMedecin(medecinTest.getId());

        assertNotNull(result);
        assertTrue(result.size() >= 2);
    }
}