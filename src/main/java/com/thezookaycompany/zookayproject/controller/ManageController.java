package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidCageException;
import com.thezookaycompany.zookayproject.model.dto.AnimalDto;
import com.thezookaycompany.zookayproject.model.dto.AnimalResponse;
import com.thezookaycompany.zookayproject.model.dto.CageDto;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.AnimalSpecies;
import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.AnimalRepository;
import com.thezookaycompany.zookayproject.repositories.CageRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.AnimalService;
import com.thezookaycompany.zookayproject.services.CageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/trainer")
// Note that: Staff and Admin can use these functions of trainer, show I set the name 'ManageController'
public class ManageController {

    @Autowired
    private CageRepository cageRepository;

    @Autowired
    private CageService cageService;

    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AnimalService animalService;

    // TRUY XUẤT DỮ LIỆU: VIEW (get) //
    // Hàm này để truy xuất tìm Zoo Cage dựa trên Zoo Area
    @GetMapping("/get-cage/{zooAreaId}")
    public List<Cage> getCagesByZooArea(@PathVariable String zooAreaId) {

        ZooArea zooArea = zooAreaRepository.getZooAreaByZooAreaId(zooAreaId);
        return cageRepository.findCagesByZooArea(zooArea);
    }

    // Hàm này để lấy tất cả cage đang có
    @GetMapping("/get-cage")
    public List<Cage> getAllCages() {
        return cageRepository.findAll();
    }

    // Lấy zoo area hiện đang có để frontend làm thẻ select khi chuẩn bị tạo cage
    @GetMapping("/get-zoo-area")
    public List<ZooArea> getAllZooArea() {
        return zooAreaRepository.findAll();
    }

    // Truy xuất dữ liệu dựa vào keyword description (search keyword)
    @GetMapping("/get-cage-desc/{keyword}")
    public List<Cage> getCagesByDescription(@PathVariable String keyword) {
        return cageRepository.findCagesByDescriptionContainingKeyword(keyword);
    }

    // Hàm này để lấy tất cả cage dựa vào capacity TĂNG DẦN
    @GetMapping("/get-cage/ascending")
    public List<Cage> getCagesByCapacityAscending() {
        return cageRepository.findAllByCapacityAsc();
    }

    // Hàm này để lấy tất cả cage dựa vào capacity GIẢM DẦN
    @GetMapping("/get-cage/descending")
    public List<Cage> getCagesByCapacityDescending() {
        return cageRepository.findAllByCapacityDesc();
    }
    // Tạo thêm Chuồng: CREATE //

    // Hàm này để tạo thêm chuồng mới dựa vào Zoo Area
    @PostMapping("/create-cage")
    public Cage createCage(@RequestBody CageDto cageDto) {
        return cageService.createCage(cageDto);
    }

    // Update Cage: UPDATE //

    @PutMapping("/update-cage")
    public ResponseEntity<String> updateCage(@RequestBody CageDto cageDto) {
        String updateResponse = cageService.updateCage(cageDto);

        if (updateResponse.startsWith("Cage updated successfully.")) {
            return ResponseEntity.ok(updateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }
    // Remove cage: DELETE //

    // Delete one ID
    @DeleteMapping("/remove-cage/{cageId}")
    public ResponseEntity<String> removeCage(@PathVariable String cageId) {
        try {
            String deletedCageId = cageService.removeCage(cageId);
            return ResponseEntity.ok("Deleted cage id: " + deletedCageId);
        } catch (InvalidCageException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cage not found with ID: " + cageId);
        }
    }
    @GetMapping("/get-animal")
    public AnimalResponse getAllAnimals(){
        return animalService.getAllAnimal();
    }

    @GetMapping("/get-animal/{animalId}")
    Animal findAnimalByAnimalID(@PathVariable("animalId") Integer animalId) {

        return animalService.findAnimalByAnimalID(animalId);

    }

    @GetMapping("/get-animal-species/{speciesId}")
    AnimalSpecies findAnimalSpeciesByAnimalID(@PathVariable("speciesId") Integer speciesId) {

        return animalService.findAnimalByAnimalSpeciesID(speciesId);

    }

    @PostMapping("/create-animal") //chua test duoc
    public Animal createAnimal(@RequestBody AnimalDto animalDto) {
        return animalService.createAnimal(animalDto);
    }


    @PutMapping("/update-animal") //chua test duoc
    public ResponseEntity<String> updateAnimal(@RequestBody AnimalDto animalDto) {
        String updateResponse = animalService.updateAnimal(animalDto);

        if (updateResponse.startsWith("Animal updated successfully.")) {
            return ResponseEntity.ok(updateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }
    @DeleteMapping("/remove-animal/{animalId}")
    public ResponseEntity<String> removeAnimal(@PathVariable Integer animalId) {
        try {
            String deletedAnimalId = animalService.removeAnimal(animalId);
            return ResponseEntity.ok("Animal cage id: " + deletedAnimalId);
        } catch (InvalidCageException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal not found with ID: " + animalId);
        }
    }
    @PostMapping("/create-animal-species") //chua test duoc
    public ResponseEntity<String> createAnimalSpecies(@RequestBody AnimalSpecies animalSpecies) {
        String response = animalService.createAnimalSpecies(animalSpecies);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update-animal-species") //chua test duoc
    public ResponseEntity<String> updateAnimalSpecies(@RequestBody AnimalDto animalDto) {
        String updateResponse = animalService.updateAnimalSpecies(animalDto);

        if (updateResponse.startsWith("Animal Species updated successfully.")) {
            return ResponseEntity.ok(updateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }

    @DeleteMapping("/remove-animal-species/{speciesId}")
    public ResponseEntity<String> removeAnimalSpecies(@PathVariable Integer speciesId) {
        try {
            String deletedAnimalSpId = animalService.removeAnimalSpecies(speciesId);
            return ResponseEntity.ok("Animal Species cage id: " + deletedAnimalSpId);
        } catch (InvalidCageException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Animal Species not found with ID: " + speciesId);
        }
    }
    @GetMapping("/get-animal/height-ascending")
    public List<Animal> getAnimalsByHeightAscending() {
        return animalRepository.findAllByHeightAsc();
    }
    @GetMapping("/get-animal/height-descending")
    public List<Animal> getAnimalsByHeightDescending() {
        return animalRepository.findAllByHeightDesc();
    }
    @GetMapping("/get-animal/weight-ascending")
    public List<Animal> getAnimalsByWeightAscending() {
        return animalRepository.findAllByWeightAsc();
    }
    @GetMapping("/get-animal/weight-descending")
    public List<Animal> getAnimalsByWeightDescending() {
        return animalRepository.findAllByWeightDesc();
    }
    @GetMapping("/get-animal/age-ascending")
    public List<Animal> getAnimalsByAgeAscending() {
        return animalRepository.findAllByAgeAsc();
    }
    @GetMapping("/get-animal/age-descending")
    public List<Animal> getAnimalsByAgeDescending() {
        return animalRepository.findAllByAgeDesc();
    }





}
