package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidAnimalException;
import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.dto.AnimalSpeciesDto;
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
import java.util.regex.Pattern;

@Service
public class AnimalServiceImpl implements AnimalService {

    private static final String CAGE_ID_REGEX = "A\\d{4}";

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private CageRepository cageRepository;
    @Autowired
    private AnimalSpeciesRepository animalSpeciesRepository;

    @Override
    public Animal findAnimalByAnimalID(Integer animalId) {
        return animalRepository.findAnimalsByAnimalId(animalId);
    }
    @Override //chua test duoc
    public String createAnimal(AnimalDto animalDto) {
        // Check validate
        if(animalDto.getAge() <= 0) {
            return "The age cannot be empty or less than zero";
        }

        if(animalDto.getWeight() <= 0.0) {
            return "The weight field must be above 0";
        }

        if(animalDto.getHeight() <= 0.0) {
            return "The height field must be above 0";
        }

        if(animalDto.getDescription() == null || animalDto.getDescription().isEmpty() || animalDto.getDescription().length() >= 256) {
            return "The description cannot be empty or greater than 256 characters";
        }

        if(animalDto.getName() == null || animalDto.getName().isEmpty() || animalDto.getName().length() >= 21) {
            return "The name cannot be empty or greater than 21 characters";
        }

        if(animalDto.getCageId() == null || animalDto.getCageId().isEmpty() || !Pattern.matches(CAGE_ID_REGEX, animalDto.getCageId())) {
            return "The cage id field cannot be empty, please fill it by format Axxxx";
        }

        if(animalDto.getSpeciesId() == null) {
            return "The species id field cannot be empty";
        }

        //Create
        Cage cage = cageRepository.findById(animalDto.getCageId()).orElse(null);
        if(cage == null) {
            return "The cage id " + animalDto.getCageId() + " cannot be found, please try other";
        }

        AnimalSpecies animalSpecies = animalSpeciesRepository.findById(animalDto.getSpeciesId()).orElse(null);
        if(animalSpecies == null) {
            return "The animal species " + animalDto.getSpeciesId() +  " cannot be found, please try other";
        }
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
        animalRepository.save(newAnimal);

        return "New animal has been added successfully";
    }



    @Override
    public String updateAnimal(AnimalDto animalDto) {
        if(animalDto.getAnimalId() == null) {
            return "Animal ID is empty, failed to update";
        }
        if(animalDto.getAge() <= 0) {
            return "The age cannot be empty or less than zero";
        }

        if(animalDto.getWeight() <= 0.0) {
            return "The weight field must be above 0";
        }

        if(animalDto.getHeight() <= 0.0) {
            return "The height field must be above 0";
        }

        if(animalDto.getDescription() == null || animalDto.getDescription().isEmpty() || animalDto.getDescription().length() >= 256) {
            return "The description cannot be empty or greater than 256 characters";
        }

        if(animalDto.getName() == null || animalDto.getName().isEmpty() || animalDto.getName().length() >= 21) {
            return "The name cannot be empty or greater than 21 characters";
        }

        if(animalDto.getCageId() == null || animalDto.getCageId().isEmpty() || !Pattern.matches(CAGE_ID_REGEX, animalDto.getCageId())) {
            return "The cage id field cannot be empty, please fill it by format Axxxx";
        }

        if(animalDto.getSpeciesId() == null) {
            return "The species id field cannot be empty";
        }

        //Create
        Cage cage = cageRepository.findById(animalDto.getCageId()).orElse(null);
        if(cage == null) {
            return "The cage id " + animalDto.getCageId() + " cannot be found, please try other";
        }

        AnimalSpecies animalSpecies = animalSpeciesRepository.findById(animalDto.getSpeciesId()).orElse(null);
        if(animalSpecies == null) {
            return "The animal species " + animalDto.getSpeciesId() +  " cannot be found, please try other";
        }
        Animal existingAnimal = animalRepository.findById(animalDto.getAnimalId()).orElse(null);
        if (existingAnimal != null) {
            // Update the fields of the existing animal with the values from the DTO
            existingAnimal.setAge(animalDto.getAge());
            existingAnimal.setDescription(animalDto.getDescription());
            existingAnimal.setGender(animalDto.getGender());
            existingAnimal.setHeight(animalDto.getHeight());
            existingAnimal.setName(animalDto.getName());
            existingAnimal.setWeight(animalDto.getWeight());

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
    public List<AnimalSpecies> getAllAnimalSpecies() {
        return animalSpeciesRepository.findAll();
    }


    @Override
    public Animal findAnimalWithSpeciesAndCage(Integer animalId) {
        return animalRepository.findAnimalWithSpeciesAndCage(animalId);
    }

    @Override
    public String createAnimalSpecies(AnimalSpeciesDto animalSpeciesDto) {
        if( animalSpeciesDto.getGroups() == null || animalSpeciesDto.getGroups().isEmpty() || animalSpeciesDto.getGroups().length() > 100) {
            return "Invalid data in group field";
        }

        if(animalSpeciesDto.getName() == null || animalSpeciesDto.getName().isEmpty() || animalSpeciesDto.getName().length() > 30) {
            return "Invalid data in name field";
        }
        AnimalSpecies animalSpecies = new AnimalSpecies();
        if(animalSpeciesDto.getSpeciesId() != null) {
            AnimalSpecies temp = animalSpeciesRepository.findById(animalSpeciesDto.getSpeciesId()).orElse(null);
            if(temp != null) {
                return "This ID has already existed";
            }
            animalSpecies.setSpeciesId(animalSpeciesDto.getSpeciesId());
        }
        animalSpecies.setGroups(animalSpeciesDto.getGroups());
        animalSpecies.setName(animalSpeciesDto.getName());

        animalSpeciesRepository.save(animalSpecies);

        return "Animal Species created successfully.";
    }

    @Override
    public String updateAnimalSpecies(AnimalSpeciesDto animalSpeciesDto) {
        if(animalSpeciesDto.getSpeciesId() == null) {
            return "Please input Species ID field";
        }
        if( animalSpeciesDto.getGroups() == null || animalSpeciesDto.getGroups().isEmpty() || animalSpeciesDto.getGroups().length() > 100) {
            return "Invalid data in group field";
        }

        if(animalSpeciesDto.getName() == null || animalSpeciesDto.getName().isEmpty() || animalSpeciesDto.getName().length() > 30) {
            return "Invalid data in name field";
        }
        AnimalSpecies existingAnimalSpecies = animalSpeciesRepository.findById(animalSpeciesDto.getSpeciesId()).orElse(null);
        if (existingAnimalSpecies != null) {


            existingAnimalSpecies.setName(animalSpeciesDto.getName());
            existingAnimalSpecies.setGroups(animalSpeciesDto.getGroups());
            // Save the updated AnimalSpecies entity to the database
            animalSpeciesRepository.save(existingAnimalSpecies);

            return "Animal Species updated successfully";
        } else {
            return "Animal Species not found with ID: " + animalSpeciesDto.getSpeciesId();
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