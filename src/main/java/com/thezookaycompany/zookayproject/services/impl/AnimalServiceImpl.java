package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidAnimalException;
import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.repositories.AnimalRepository;
import com.thezookaycompany.zookayproject.repositories.AnimalSpeciesRepository;
import com.thezookaycompany.zookayproject.repositories.CageRepository;
import com.thezookaycompany.zookayproject.services.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private CageRepository cageRepository;
    @Autowired
    private AnimalSpeciesRepository animalSpeciesRepository;
    @Override

    public Animal findAnimalByAnimalGroup(String groups) {
        return null;
    }

    @Override
    public Animal findAnimalByAnimalID(Integer animalId) {
        return animalRepository.findAnimalsByAnimalId(animalId);
    }
    @Override //chua test duoc
    public Animal createAnimal(AnimalDto animalDto) {
        //Find Animal
        if (animalRepository.findById(animalDto.getAnimalId()).isPresent()) {
            throw new InvalidAnimalException("This Animal ID has existed.");
        }
        //Create
        Cage cage = cageRepository.getReferenceById(animalDto.getCageID());
        AnimalSpecies animalSpecies = animalSpeciesRepository.getReferenceById(animalDto.getSpeciesId());
        Animal newAnimal = new Animal();
        newAnimal.setAnimalId(animalDto.getAnimalId());
        newAnimal.setAge(animalDto.getAge());
        newAnimal.setDescription(animalDto.getDescription());
        newAnimal.setGender(animalDto.getGender());
        newAnimal.setHeight(animalDto.getHeight());
        newAnimal.setName(animalDto.getName());
        newAnimal.setWeight(animalDto.getWeight());
        newAnimal.setCage(cage);
        newAnimal.setSpecies(animalSpecies);
        return animalRepository.save(newAnimal);
    }



    @Override //chua test duoc
    public String updateAnimal(AnimalDto animalDto) {
        Animal existingAnimal = animalRepository.findById(animalDto.getAnimalId()).orElse(null);
        if (existingAnimal != null) {
            // Update the fields of the existing animal with the values from the DTO
            existingAnimal.setAge(animalDto.getAge());
            existingAnimal.setDescription(animalDto.getDescription());
            existingAnimal.setGender(animalDto.getGender());
            existingAnimal.setHeight(animalDto.getHeight());
            existingAnimal.setName(animalDto.getName());
            existingAnimal.setWeight(animalDto.getWeight());

            // Update the associated Cage and Species as needed (similar to your createAnimal method)
            Cage cage = cageRepository.getReferenceById(animalDto.getCageID());
            AnimalSpecies animalSpecies = animalSpeciesRepository.getReferenceById(animalDto.getSpeciesId());
            existingAnimal.setCage(cage);
            existingAnimal.setSpecies(animalSpecies);

            // Save the updated Animal entity to the database

            animalRepository.save(existingAnimal);

            return "Animal updated successfully";
        } else {
            return "Animal not found with ID: " + animalDto.getAnimalId();
        }


    }


    @Override
    public String removeAnimal(Integer id) {

        Animal animal = animalRepository.findById(id).orElseThrow(() -> new InvalidAnimalException("Not found this Animal ID to delete."));
        animalRepository.delete(animal);
        return String.valueOf(animal.getAnimalId());
    }

    @Override
    public AnimalResponse getAllAnimal() {
        List<Animal> animalList = animalRepository.findAll();
        return new AnimalResponse(animalList);
    }
    @Override
    public Animal findAnimalWithSpeciesAndCage(Integer animalId) {
        return animalRepository.findAnimalWithSpeciesAndCage(animalId);
    }



    @Override //chua test duoc
    public String createAnimalSpecies(AnimalSpecies animalSpecies) {

        if (animalSpeciesRepository.findById(animalSpecies.getSpeciesId()).isPresent()) {
            throw new InvalidAnimalException("This Animal Species ID has existed.");
        }
        AnimalSpecies newAnimalSpecies = new AnimalSpecies();
        newAnimalSpecies.setSpeciesId(animalSpecies.getSpeciesId());
        newAnimalSpecies.setName(animalSpecies.getName());
        newAnimalSpecies.setGroups(animalSpecies.getGroups());
        animalSpeciesRepository.save(newAnimalSpecies);
        return "Animal Species created successfully.";
    }

    @Override // chua test duoc
    public String updateAnimalSpecies(AnimalDto animalDto) {
        AnimalSpecies existingAnimalSpecies = animalSpeciesRepository.findById(animalDto.getSpeciesId()).orElse(null);
        if (existingAnimalSpecies != null) {
            // Update the fields of the existing animal species with the values from the DTO
            existingAnimalSpecies.setName(animalDto.getSpeciesName());
            existingAnimalSpecies.setGroups(animalDto.getGroups());

            // Save the updated AnimalSpecies entity to the database
            animalSpeciesRepository.save(existingAnimalSpecies);

            return "Animal Species updated successfully";
        } else {
            return "Animal Species not found with ID: " + animalDto.getSpeciesId();
        }
    }

    @Override
    public String removeAnimalSpecies(Integer id) {
        AnimalSpecies animalSpecies = animalSpeciesRepository.findById(id).orElseThrow(() -> new InvalidAnimalException("Not found this Animal ID to delete."));
        animalSpeciesRepository.delete(animalSpecies);
        return String.valueOf(animalSpecies.getSpeciesId());
    }

    @Override
    public AnimalSpecies findAnimalByAnimalSpeciesID(Integer speciesId) {

        return animalSpeciesRepository.findAnimalSpeciesBySpeciesId(speciesId);
    }
}