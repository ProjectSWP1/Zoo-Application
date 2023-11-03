package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.dto.AnimalSpeciesDto;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnimalService {
    Animal findAnimalByAnimalID(Integer animalId);
    String createAnimal(AnimalDto animalDto);
    String updateAnimal(AnimalDto animalDto);
    String removeAnimal(Integer id);
    AnimalResponse getAllAnimal();
    List<AnimalSpecies> getAllAnimalSpecies();
    Animal findAnimalWithSpeciesAndCage(Integer animalId);

    String createAnimalSpecies(AnimalSpeciesDto animalSpeciesDto);
    String updateAnimalSpecies(AnimalSpeciesDto animalSpeciesDto);
    String removeAnimalSpecies(Integer id);

    AnimalSpecies findAnimalByAnimalSpeciesID(Integer speciesId);

    List<Animal> findAllByHeightAsc();
    List<Animal> findAllByHeightDesc();
    List<Animal> findAllByWeightAsc();
    List<Animal> findAllByWeightDesc();
    List<Animal> findAllByAgeAsc();
    List<Animal> findAllByAgeDesc();

    void uploadAnimalImage(Integer animalId, byte[] imageBytes, String format) throws IOException;
    void deleteAnimalImage(Integer animalId);
    byte[] getAnimalImageById(Integer animalId);

    long countAnimals();
    long countAnimalSpecies();

}
