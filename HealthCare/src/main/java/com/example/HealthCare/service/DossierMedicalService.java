package com.example.HealthCare.service;

import com.example.HealthCare.dto.*;
import com.example.HealthCare.entity.DossierMedical;
import com.example.HealthCare.entity.Patient;
import com.example.HealthCare.entity.RendezVous;
import com.example.HealthCare.mapper.DossierMedicalMapper;
import com.example.HealthCare.repository.DossierMedicalRepo;
import com.example.HealthCare.repository.PatientRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class DossierMedicalService {

    private final DossierMedicalMapper dossierMedicalMapper;
    private final DossierMedicalRepo dossierMedicalRepo;
    private final PatientRepo patientRepo;

    public DossierMedicalService(DossierMedicalMapper dossierMedicalMapper, DossierMedicalRepo dossierMedicalRepo, PatientRepo patientRepo) {
        this.dossierMedicalMapper=dossierMedicalMapper;
        this.dossierMedicalRepo=dossierMedicalRepo;
        this.patientRepo=patientRepo;
    }


    @Transactional
    @CacheEvict(value = "DOSSIER_CACHE",allEntries = true)
    public DossierMedicalResponseDto createDossier(DossierMedicalRequestDto dossierRequestDto) {

        DossierMedical dossierMedical=dossierMedicalMapper.toEntity(dossierRequestDto);
        Patient patient = patientRepo.findById(dossierRequestDto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        dossierMedical.setPatient(patient);
        dossierMedicalRepo.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    @Transactional
    @CacheEvict(value = "DOSSIER_CACHE",key ="#patientid")
    public DossierMedicalResponseDto ajouterDiagnostic(Long patientid,String diagnostic) {
        DossierMedical dossier = dossierMedicalRepo.findById(patientid)
                .orElseThrow(() -> new RuntimeException("Dossier not found"));
        dossier.setDiagnostic(diagnostic);
        dossierMedicalRepo.save(dossier);
        return dossierMedicalMapper.toDto(dossier);
    }

    @Transactional
    @CacheEvict(value = "DOSSIER_CACHE",key ="#patientId")
    public DossierMedicalResponseDto ajouterObeservation(Long patientId,String observation) {
        DossierMedical dossierMedical=dossierMedicalRepo.findById(patientId).orElseThrow(()->new RuntimeException("Dossier not found"));
        dossierMedical.setObservation(observation);
        dossierMedicalRepo.save(dossierMedical);
        return dossierMedicalMapper.toDto(dossierMedical);
    }

    @Transactional
    @Cacheable(value = "DOSSIER_CACHE",key = "#id")
    public  DossierMedicalResponseDto findDossierMedicalById(Long id){
        DossierMedical dossier = dossierMedicalRepo.findById(id).orElseThrow(()->new RuntimeException("Dossier not found"));
        return  dossierMedicalMapper.toDto(dossier);
    }
    @Transactional
    @Cacheable(value = "DOSSIER_CACHE",
            key = "#page +'-'+ #size +'-' + #sortBy")
    public PageResponseDTO<DossierMedicalResponseDto> getAllDossier(
            int page,
            int size,
            String sortBy
    ){

        Pageable pageable =
                PageRequest.of(page,size,Sort.by(sortBy).ascending());

        Page<DossierMedical> medicals =
                dossierMedicalRepo.findAll(pageable);


        Page<DossierMedicalResponseDto> dtoPage =
                medicals.map(dossierMedicalMapper::toDto);


        return new PageResponseDTO<>(
                dtoPage.getContent(),
                dtoPage.getNumber(),
                dtoPage.getSize(),
                dtoPage.getTotalElements(),
                dtoPage.getTotalPages()
        );
    }
    @Transactional
    public String telechargePDF(Long patientId) throws FileNotFoundException, DocumentException {
        Patient  patient = patientRepo.findById(patientId).orElseThrow(()->new RuntimeException("Patient not found with this id "+ patientId));

        Document document= new Document();
        String fileName= "dossier_medical"+ patientId +".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        document.add(new Paragraph("=== DOSSIER MEDICAL ==="));
        document.add(new Paragraph("ID Patient: " + patient.getId()));
        document.add(new Paragraph("Nom" + patient.getNom()));
        document.add(new Paragraph("Perenom " + patient.getPrenom()));
        document.add(new Paragraph(String.valueOf("Date naissance" + patient.getDateNaissance())));
        document.add(new Paragraph("TelEphone "+ patient.getTelephone()));
        document.add(new Paragraph( "Email " + patient.getEmail()));

        if(patient.getDossierMedical() != null){
            document.add(new Paragraph("Dossier medical "+patient.getDossierMedical().getId()));
        }
        document.close();

       return fileName;
    }

}
