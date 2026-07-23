package com.example.HealthCare.service;

import com.example.HealthCare.dto.PageResponseDTO;
import com.example.HealthCare.dto.RendezVouRequestDTO;
import com.example.HealthCare.dto.RendezVousResponseDTO;
import com.example.HealthCare.entity.*;
import com.example.HealthCare.mapper.RendezVousMapper;
import com.example.HealthCare.repository.DossierMedicalRepo;
import com.example.HealthCare.repository.MedecinRepo;
import com.example.HealthCare.repository.PatientRepo;
import com.example.HealthCare.repository.RendezVousRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Status;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RendezVousService {
    private final RendezVousRepo rendezVousRepo;
    private final RendezVousMapper rendezVousMapper;
    private final PatientRepo patientRepo;
    private final MedecinRepo  medecinRepo;

    private  final DossierMedicalRepo dossierMedicalRepo;

    public RendezVousService(RendezVousRepo rendezVousRepo, RendezVousMapper rendezVousMapper, PatientRepo patientRepo, MedecinRepo medecinRepo, DossierMedicalRepo dossierMedicalRepo) {
        this.rendezVousRepo = rendezVousRepo;
        this.rendezVousMapper = rendezVousMapper;
        this.patientRepo = patientRepo;
        this.medecinRepo = medecinRepo;
        this.dossierMedicalRepo = dossierMedicalRepo;
    }

    @Transactional
    @CacheEvict(value = "RENDEZ_VOUS_CACHE", allEntries = true)
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
    @CachePut(value = "RENDEZ_VOUS_CACHE",key = "#id")
    public RendezVousResponseDTO updateRendezVous(Long id, RendezVouRequestDTO requestDTO){
        RendezVous rendezVous =rendezVousRepo.findById(id).orElseThrow(()->new RuntimeException("RendezVous Not Found"));
       Patient patient= patientRepo.findById(requestDTO.getPatientId()).orElseThrow(()->new RuntimeException("Patient Not Found"));
       Medecin medecin= medecinRepo.findById(requestDTO.getMedecinId()).orElseThrow(()->new RuntimeException("Medecin Not Found"));

        rendezVousMapper.update(requestDTO,rendezVous);
        rendezVous.setPatient(patient);
        rendezVous.setMedecin(medecin);
        RendezVous updateRendez= rendezVousRepo.save(rendezVous);
        return rendezVousMapper.toDto(updateRendez);
    }

    @Transactional
    @Cacheable(value = "RENDEZ_VOUS_CACHE",
            key = "#page + '-' + #size + '-' + #sortBy")
    public PageResponseDTO<RendezVousResponseDTO> getAllRendezVous(
            int page,
            int size,
            String sortBy
    ){

        Pageable pageable =
                PageRequest.of(page,size,Sort.by(sortBy).ascending());


        Page<RendezVous> rendezVous =
                rendezVousRepo.findAll(pageable);


        Page<RendezVousResponseDTO> dtoPage =
                rendezVous.map(rendezVousMapper::toDto);


        return new PageResponseDTO<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages()
        );
    }

    @Transactional
    @Cacheable(value = "RENDEZVOUS_PATIENT_CACHE", key = "#patientId")
    public List<RendezVousResponseDTO> rechercheRendezVousPatient(Long patientId){
        List<RendezVous>listOfPatient=rendezVousRepo.findByPatientId(patientId);
        return rendezVousMapper.toDto(listOfPatient);
    }

    @Transactional
    @Cacheable(value = "RENDEZVOUS_MEDECIN_CACHE", key = "#medecinId")
    public List<RendezVousResponseDTO> rechercheRendezVousMedecin(Long medecinId){
        List<RendezVous> listOfMedecin=rendezVousRepo.findByMedecinId(medecinId);
        return rendezVousMapper.toDto(listOfMedecin);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "RENDEZVOUS_PATIENT_CACHE", allEntries = true),
            @CacheEvict(value = "RENDEZVOUS_MEDECIN_CACHE", allEntries = true)
    })
    public RendezVousResponseDTO annulerRendezVous(Long id){
        RendezVous rendezVous= rendezVousRepo.findById(id).orElseThrow(()->new RuntimeException("RendezVous Not Found"));
        rendezVous.setStatut(StatutRendezVous.ANNULE);
        rendezVousRepo.save(rendezVous);;
        return rendezVousMapper.toDto(rendezVous);
    }

//    @Transactional
//    public Page<RendezVousResponseDTO> getRendezVousByStatu( Long medecin_id,StatutRendezVous statut,Pageable  pageable){
//        Medecin medecin =medecinRepo.findById(medecin_id).orElseThrow(()->new RuntimeException("not found"));
//       return  rendezVousRepo.findByStatut(statut,medecin_id,pageable)
//               .map(rendezVousMapper::toDto);
//    }
    @Transactional
    public Page<RendezVousResponseDTO> searchByStatus(
            StatutRendezVous status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return rendezVousRepo.findByStatut(status, pageable)
                .map(rendezVousMapper::toDto);
    }

    public Page<RendezVousResponseDTO> findByDateRendezVous(LocalDate datee, int page, int size) {
        Pageable pageable =PageRequest.of(page,size);
        Page<RendezVous> rendezVous= rendezVousRepo.findByDateRendezVous(pageable,datee);
        return  rendezVous.map(rendezVousMapper::toDto);
    }



    @Transactional
    public String generateRendezVousPDF(Long id)throws FileNotFoundException, DocumentException {
        List<RendezVous> rendezVousList = rendezVousRepo.findByPatientId(id);
        Patient patient =patientRepo.findById(id).orElseThrow(()->new RuntimeException("Patient not found"));

        String fileName= "List Rendez_vous d'un patient"+ id + ".pdf";
        Document document = new Document();

        PdfWriter.getInstance(document,new FileOutputStream(fileName));
        document.open();
        document.add(new Paragraph("##########List Rendez_vous d'un patient########"));
        document.add(new Paragraph("Patient "+ patient.getNom() +" " +patient.getPrenom()));
        document.add(new Paragraph(patient.getListeRendezVous().size()));
        PdfPTable table = new PdfPTable(3);
        table.addCell("Date");
        table.addCell("Heure");
        table.addCell("Statut");
        for(RendezVous rendezVous : rendezVousList){
            table.addCell(rendezVous.getDateRendezVous().toLocalDate().toString());
            table.addCell(rendezVous.getDateRendezVous().toLocalTime().toString());
            table.addCell(rendezVous.getStatut().toString());
        }
        document.add(table);
        document.close();
        return fileName;
    }

    @Transactional
    public String generatrSimpleRapport(Long patientID) throws FileNotFoundException, DocumentException {
        Patient patient= patientRepo.findById(patientID).orElseThrow(()->new RuntimeException("Patient not found with this id "+patientID));
        List<RendezVous> list = rendezVousRepo.findByPatientId(patientID);
        DossierMedical dossierMedical= patient.getDossierMedical();

        String rapportName = "simple_rapport_" + patientID + ".pdf";        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream(rapportName));
        document.open();
        document.add(new Paragraph("RAPPORT MÉDICAL"));
        document.add(new Paragraph("ID "+patient.getId()));
        document.add(new Paragraph("Patient "+ patient.getNom() + " "+patient.getPrenom()));
        document.add(new Paragraph("Telephone "+patient.getTelephone()));
        document.add(new Paragraph("Email "+patient.getEmail()));
        document.add(new Paragraph("-------------------------------------"));
        document.add(new Paragraph("DOSSIER MÉDICAL:"));
        document.add(new Paragraph("ID "+dossierMedical.getId()));
        document.add(new Paragraph("Diagnostic "+dossierMedical.getDiagnostic()));
        document.add(new Paragraph("Observation "+dossierMedical.getObservation()));
        document.add(new Paragraph("Date Creation "+dossierMedical.getDateCreation()));
        document.add(new Paragraph("-----------------------------------------------"));
        document.add(new Paragraph("RENDEZ-VOUS:"));

        for (RendezVous rvd: list){
          document.add(new Paragraph("Date Rendez_vous"+ rvd.getDateRendezVous()));
            if(rvd.getMedecin() != null) {
                document.add(new Paragraph("Medecin " + rvd.getMedecin().getNom()));
            }
            document.add(new Paragraph("Statut"+rvd.getStatut()));
        }
        document.add(new Paragraph("Total rendez_vous"+ list.size()));
        document.add(new Paragraph("-------------------------------------"));
        document.add(new Paragraph("Date generation : " + LocalDate.now()));
        document.close();
        return rapportName;
    }

}
