package com.example.HealthCare.service;


import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.entity.Patient;

import com.example.HealthCare.entity.StatutRendezVous;
import com.example.HealthCare.repository.MedecinRepo;
import com.example.HealthCare.repository.PatientRepo;
import com.example.HealthCare.repository.RendezVousRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

import static org.junit.jupiter.api.Assertions.*;


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

    private  Patient patientTest;
    private Medecin medecinTest;
    private RendezVousResponseDTO rendezVousCreated;

    @BeforeEach
    void  setup(){
        Patient patient =new Patient();
        patient.setNom("samira");
        patient.setPrenom("El boussidi");
        patient.setEmail(UUID.randomUUID()+"@gmail.com");
        patient.setTelephone("98765432");

        patientTest= patientRepo.save(patient);

        Medecin medecin= new Medecin();
        medecin.setNom("Isamil");
        medecin.setSpecialite("cardiology");
        medecin.setEmail(UUID.randomUUID()+"@gmail.com");
        medecin.setTelephone("98765432");
        medecinTest= medecinRepo.save(medecin);

        RendezVouRequestDTO requestDTO = new RendezVouRequestDTO();
        requestDTO.setPatientId(patientTest.getId());
        requestDTO.setMedecinId(medecinTest.getId());

        rendezVousCreated = service.createRendezVous(requestDTO);
    }

    @Test
    public void ajouterRendezVousTest() {

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
    void modifierRendezVousTest(){

        RendezVouRequestDTO updated= new RendezVouRequestDTO();
        updated.setPatientId(patientTest.getId());
        updated.setMedecinId(medecinTest.getId());

        updated.setStatut(StatutRendezVous.CONFIRME);
        RendezVousResponseDTO responseDT= service.updateRendezVous(rendezVousCreated.getId(),updated);

        assertNotNull(responseDT);
        assertEquals(StatutRendezVous.CONFIRME,responseDT.getStatut());


    }
    @Test
    void annulerRendez_vousTest(){

        RendezVousResponseDTO responseDTO=service.annulerRendezVous(rendezVousCreated.getId());
        assertNotNull(responseDTO);
        assertEquals(StatutRendezVous.ANNULE,responseDTO.getStatut());
    }

    @Test
    void  listerRendezVous(){

        List<RendezVousResponseDTO>list=service.getAllRendezVous();
        assertNotNull(list);
        assertTrue(list.size()>0);
    }

    @Test
    void rechercheRendezVousPatientTest() {

        RendezVouRequestDTO r1 = new RendezVouRequestDTO();
        r1.setPatientId(patientTest.getId());
        r1.setMedecinId(medecinTest.getId());
        service.createRendezVous(r1);

        RendezVouRequestDTO r2 = new RendezVouRequestDTO();
        r2.setPatientId(patientTest.getId());
        r2.setMedecinId(medecinTest.getId());
        service.createRendezVous(r2);

        List<RendezVousResponseDTO> result =
                service.rechercheRendezVousPatient(patientTest.getId());

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void rechercheRendezVousMedecinTest() {
        RendezVouRequestDTO r1 = new RendezVouRequestDTO();
        r1.setPatientId(patientTest.getId());
        r1.setMedecinId(medecinTest.getId());
        service.createRendezVous(r1);

        RendezVouRequestDTO r2 = new RendezVouRequestDTO();
        r2.setPatientId(patientTest.getId());
        r2.setMedecinId(medecinTest.getId());
        service.createRendezVous(r2);


        List<RendezVousResponseDTO> result =
                service.rechercheRendezVousMedecin(medecinTest.getId());

        assertNotNull(result);

        assertEquals(3,result.size());
    }
}