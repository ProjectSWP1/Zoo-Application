package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.dto.AnimalSpeciesDto;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;

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







}
