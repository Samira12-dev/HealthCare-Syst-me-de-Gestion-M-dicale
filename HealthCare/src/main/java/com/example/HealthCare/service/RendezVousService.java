package com.example.HealthCare.service;

import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.entity.RendezVous;
import com.example.HealthCare.entity.StatutRendezVous;
import com.example.HealthCare.mapper.RendezVousMapper;
import com.example.HealthCare.repository.MedecinRepo;
import com.example.HealthCare.repository.PatientRepo;
import com.example.HealthCare.repository.RendezVousRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RendezVousService {
    private final RendezVousRepo rendezVousRepo;
    private final RendezVousMapper rendezVousMapper;
    private final PatientRepo patientRepo;
    private final MedecinRepo  medecinRepo;

    public RendezVousService(RendezVousRepo rendezVousRepo, RendezVousMapper rendezVousMapper, PatientRepo patientRepo, MedecinRepo medecinRepo) {
        this.rendezVousRepo = rendezVousRepo;
        this.rendezVousMapper = rendezVousMapper;
        this.patientRepo = patientRepo;
        this.medecinRepo = medecinRepo;
    }

    @Transactional
    public RendezVousResponseDTO createRendezVous(RendezVouRequestDTO  rendezVouRequestDTO) {
        RendezVous createRendezVous= rendezVousMapper.toEntity(rendezVouRequestDTO);

            Patient patient= patientRepo.findById(rendezVouRequestDTO.getPatientId()).orElseThrow(()->new RuntimeException("Patient Not Found"));
            createRendezVous.setPatient(patient);

            Medecin medecin= medecinRepo.findById(rendezVouRequestDTO.getMedecinId()).orElseThrow(()->new RuntimeException("Medecin Not Found"));
            createRendezVous.setMedecin(medecin);
        if(createRendezVous.getStatut()==null){
            createRendezVous.setStatut(StatutRendezVous.PLANIFIE);
        }
        RendezVous saveRendezVous= rendezVousRepo.save(createRendezVous);
        return rendezVousMapper.toDto(saveRendezVous);
    }

    @Transactional
    public RendezVousResponseDTO updateRendezVous(Long id, RendezVouRequestDTO requestDTO){
        RendezVous rendezVous =rendezVousRepo.findById(id).orElseThrow(()->new RuntimeException("RendezVous Not Found"));
       Patient patient= patientRepo.findById(requestDTO.getPatientId()).orElseThrow(()->new RuntimeException("Patient Not Found"));
       Medecin medecin= medecinRepo.findById(requestDTO.getMedecinId()).orElseThrow(()->new RuntimeException("Medecin Not Found"));
        rendezVous.setDateRendezVous(requestDTO.getDateRendezVous());
        rendezVous.setStatut(StatutRendezVous.CONFIRME);

        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        RendezVous updateRendez= rendezVousRepo.save(rendezVous);
        return rendezVousMapper.toDto(updateRendez);
    }

    @Transactional
    public List<RendezVousResponseDTO> getAllRendezVous(){
      List<RendezVous> rendezVousList=rendezVousRepo.findAll();
      return  rendezVousMapper.toDto(rendezVousList);
    }
    @Transactional
    public List<RendezVousResponseDTO> rechercheRendezVousPatient(Long patientId){
        List<RendezVous>listOfPatient=rendezVousRepo.findByPatientId(patientId);
        return rendezVousMapper.toDto(listOfPatient);
    }

    @Transactional
    public List<RendezVousResponseDTO> rechercheRendezVousMedecin(Long medecinId){
        List<RendezVous> listOfMedecin=rendezVousRepo.findByMedecinId(medecinId);
        return rendezVousMapper.toDto(listOfMedecin);
    }

    @Transactional
    public RendezVousResponseDTO annulerRendezVous(Long id){
        RendezVous rendezVous= rendezVousRepo.findById(id).orElseThrow(()->new RuntimeException("RendezVous Not Found"));
        rendezVous.setStatut(StatutRendezVous.ANNULE);
        rendezVousRepo.save(rendezVous);;
        return rendezVousMapper.toDto(rendezVous);
    }
}
