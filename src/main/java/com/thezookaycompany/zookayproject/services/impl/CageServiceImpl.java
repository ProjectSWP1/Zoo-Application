package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidCageException;
import com.thezookaycompany.zookayproject.model.dto.CageDto;
import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.CageRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.CageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class CageServiceImpl implements CageService {

    private static final String CAGE_ID_REGEX = "A\\d{4}";

    @Autowired
    private CageRepository cageRepository;

    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Override
    public Cage createCage(CageDto cageDto) {
        if(!Pattern.matches(CAGE_ID_REGEX, cageDto.getCageID())) {
            throw new InvalidCageException("Invalid Cage ID format. It should match the pattern Axxxx.");
        }
        //Find cage
        if(cageRepository.findById(cageDto.getCageID()).isPresent()) {
            throw new InvalidCageException("This Cage ID has existed.");
        }

        ZooArea zooArea = zooAreaRepository.findById(cageDto.getZoo_AreaID()).orElse(null);
        if(zooArea == null) {
            throw new InvalidCageException("Zoo Area not found");
        }
        Cage cage = new Cage();
        // Create a new Cage entity
        cage.setCageID(cageDto.getCageID());
        cage.setDescription(cageDto.getDescription());
        cage.setCapacity(cageDto.getCapacity());
        cage.setZooArea(zooArea);

        // Save the Cage entity in the repository
        return cageRepository.save(cage);
    }

    @Override
    public String updateCage(CageDto cageDto) {
        if(!Pattern.matches(CAGE_ID_REGEX, cageDto.getCageID())) {
            return "Invalid Cage ID format. It should match the pattern 'Axxxx'.";
        }
        ZooArea zooArea = zooAreaRepository.findById(cageDto.getZoo_AreaID()).orElse(null);

        if(zooArea == null) {
            return "No Zoo Area included, please input available Zoo Area ID.";
        }

        Cage existingCage = cageRepository.findById(cageDto.getCageID()).orElse(null);

        if (existingCage == null) {
            return "Cage not found with ID: " + cageDto.getCageID();
        }
        existingCage.setDescription(cageDto.getDescription());
        existingCage.setCapacity(cageDto.getCapacity());

        cageRepository.save(existingCage);

        return "Cage updated successfully.";
    }

    @Override
    public String removeCage(String id) {
        Cage cage = cageRepository.findById(id).orElseThrow(() -> new InvalidCageException("Not found this Cage ID to delete."));

        cageRepository.delete(cage);

        return cage.getCageID();
    }
}
