package com.example.HealthCare.service;

import com.example.HealthCare.dto.MedecinRequestDTO;
import com.example.HealthCare.dto.MedecinResponseDTO;
import com.example.HealthCare.entity.Medecin;
import com.example.HealthCare.mapper.MedecinMapper;
import com.example.HealthCare.repository.MedecinRepo;
import jakarta.transaction.Transactional;
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
   public List<MedecinResponseDTO> getAllMedecin(){
        return  medecinMapper.toDTO(medecinRepo.findAll());
   }
}

