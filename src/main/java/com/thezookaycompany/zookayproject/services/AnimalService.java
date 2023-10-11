package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;

import java.util.List;

public interface AnimalService {
    Animal findAnimalByAnimalGroup(String groups);
    Animal findAnimalByAnimalID(Integer animalId);
    Animal createAnimal(AnimalDto animalDto);
    String updateAnimal(AnimalDto animalDto);
    String removeAnimal(Integer id);

    AnimalResponse getAllAnimal();

    Animal findAnimalWithSpeciesAndCage(Integer animalId);


    String createAnimalSpecies(AnimalSpecies animalSpecies);
    String updateAnimalSpecies(AnimalDto animalDto);
    String removeAnimalSpecies(Integer id);

    AnimalSpecies findAnimalByAnimalSpeciesID(Integer speciesId);







}
