package com.example.HealthCare.service;

import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.mapper.MedecinMapper;
import com.example.HealthCare.repository.MedecinRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinService {
    private final MedecinRepo medecinRepo;
    private final MedecinMapper medecinMapper;
    public MedecinService(MedecinRepo medecinRepo, MedecinMapper medecinMapper) {
        this.medecinRepo = medecinRepo;
        this.medecinMapper = medecinMapper;
    }

    @Transactional
    public MedecinResponseDTO addMedecin(MedecinRequestDTO medecinRequestDTO) {
        if(medecinRepo.existsByEmail(medecinRequestDTO.getEmail())){
            throw  new RuntimeException("already exists");
        }
        Medecin addMedecin=medecinMapper.toEntity(medecinRequestDTO);
        medecinRepo.save(addMedecin);
        return medecinMapper.toDTO(addMedecin);
    }

    @Transactional
    public MedecinResponseDTO updaMedecin( Long id, MedecinRequestDTO medecindto){
        Medecin findMedecin= medecinRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        medecinMapper.update(medecindto,findMedecin);
        Medecin updateMedecin=medecinRepo.save(findMedecin);
        return medecinMapper.toDTO(updateMedecin);
    }

    @Transactional
    public  void deleteMedecin(Long id){
        medecinRepo.deleteById(id);
    }

    @Transactional
   public Page<MedecinResponseDTO> getAllMedecin(int page, int size, String sortBy){
        Pageable pageable= PageRequest.of(page, size, Sort.by(sortBy).ascending());
        Page<Medecin>medecins=medecinRepo.findAll(pageable);
        return medecins.map(medecinMapper::toDTO);
   }
   @Transactional
   public Page<MedecinResponseDTO> searchDoctor(
           String specialite,
           int page,
           int size
   ) {
       Pageable pageable = PageRequest.of(page, size);

       return medecinRepo.findBySpecialite(specialite, pageable)
               .map(medecinMapper::toDTO);
   }
}

