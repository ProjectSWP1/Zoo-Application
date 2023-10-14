package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidCageException;
import com.thezookaycompany.zookayproject.model.dto.*;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.AnimalRepository;
import com.thezookaycompany.zookayproject.repositories.CageRepository;
import com.thezookaycompany.zookayproject.repositories.FeedingScheduleRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.AnimalFoodServices;
import com.thezookaycompany.zookayproject.services.AnimalService;
import com.thezookaycompany.zookayproject.services.CageService;
import com.thezookaycompany.zookayproject.services.FeedingScheduleServices;
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

    private final String SUCCESS_RESPONSE = "success";

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

    // TODO: Clean code > chuyển toàn bộ cage repository sang cage services

    @Autowired
    private AnimalFoodServices animalFoodServices;
    @Autowired
    private FeedingScheduleServices feedingScheduleServices;

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + cageId);
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
    public ResponseEntity<?> createAnimal(@RequestBody AnimalDto animalDto) {
        String response = animalService.createAnimal(animalDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/update-animal")
    public ResponseEntity<?> updateAnimal(@RequestBody AnimalDto animalDto) {
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
    @PostMapping("/create-animal-species")
    public ResponseEntity<String> createAnimalSpecies(@RequestBody AnimalSpeciesDto animalSpeciesDto) {
        String response = animalService.createAnimalSpecies(animalSpeciesDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
    @PutMapping("/update-animal-species")
    public ResponseEntity<String> updateAnimalSpecies(@RequestBody AnimalSpeciesDto animalSpeciesDto) {
        String updateResponse = animalService.updateAnimalSpecies(animalSpeciesDto);

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
    // TODO: Clean code > chuyển toàn bộ animal repository sang animal services
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

    @GetMapping("/get-animal-with-species-and-cage/{animalId}")
    public Animal getAnimalWithSpeciesAndCage(@PathVariable Integer animalId) {
        return animalService.findAnimalWithSpeciesAndCage(animalId);
    }

    /////////////////////////////////////////
    /// ANIMAL FOOD FEATURES (MANAGEMENT) ///
    /////////////////////////////////////////

    /*
        @param args description, importDate, name, origin
        @return response status
    */
    @PostMapping("/add-animal-food")
    public ResponseEntity<?> addAnimalFood(@RequestBody AnimalFoodDto animalFoodDto) {
        String response = animalFoodServices.addAnimalFood(animalFoodDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/get-animal-food/{foodId}")
    public AnimalFood getAnimalFood(@PathVariable Integer foodId) {
        return animalFoodServices.getAnimalFood(foodId);
    }

    @GetMapping("/get-all-animal-food")
    public List<AnimalFood> getAllAnimalFood() {
        return animalFoodServices.getAllAnimalFood();
    }

    @GetMapping("/get-food-by-origin/{origin}")
    public List<AnimalFood> getAnimalFoodsByOrigin(@PathVariable String origin) {
        return animalFoodServices.getAnimalFoodsByOrigin(origin);
    }

    @GetMapping("/get-food-by-description/{keyword}")
    public List<AnimalFood> getAnimalFoodsByDesc(@PathVariable String keyword) {
        return animalFoodServices.getAnimalFoodsByDesc(keyword);
    }

    @GetMapping("/get-food/date-desc")
    public List<AnimalFood> getAll_AnimalFoodsByDescOfImportDate() {
        return animalFoodServices.getAll_AnimalFoodsByDescOfImportDate();
    }

    @GetMapping("/get-food/date-asc")
    public List<AnimalFood> getAll_AnimalFoodsByAscOfImportDate() {
        return animalFoodServices.getAll_AnimalFoodsByAscOfImportDate();
    }

    @GetMapping("/get-food-by-range")
    public List<AnimalFood> getAnimalFoodsFromBeginDateToEndDate(@RequestParam String fromDate, @RequestParam String toDate) {
        return animalFoodServices.getAnimalFoodsFromBeginDateToEndDate(fromDate, toDate);
    }

    @GetMapping("/get-food-by-name/{name}")
    public List<AnimalFood> getAnimalFoodsByName(@PathVariable String name) {
        return animalFoodServices.getAnimalFoodsByName(name);
    }

    /*
        @param foodId, description, importDate, name, origin
        @return response status
    */
    @PutMapping("/update-animal-food")
    public ResponseEntity<?> updateAnimalFood(@RequestBody AnimalFoodDto animalFoodDto) {
        String response = animalFoodServices.updateAnimalFood(animalFoodDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/delete-animal-food/{foodId}")
    public ResponseEntity<?> removeAnimalFood(@PathVariable Integer foodId) {
        String response = animalFoodServices.removeAnimalFood(foodId);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
    // end of animal food features

    ////////////////////////////////
    /// Feeding Schedule FEATURE ///
    ////////////////////////////////

    @GetMapping("/get-all-feedingSchedule")
    public List<FeedingSchedule> getAllFeedingSchedule() {
        return feedingScheduleServices.listAllFeedingSchedule();
    }

    @GetMapping("/get-feedingSchedule-by-foodId/{foodId}")
    public List<FeedingSchedule> getFeedingScheduleByFoodId(@PathVariable Integer foodId) {
        return feedingScheduleServices.getFeedingSchedulesByFoodId(foodId);
    }

    @GetMapping("/get-feedingSchedule-by-speciesId/{speciesId}")
    public List<FeedingSchedule> getFeedingScheduleBySpeciesId(@PathVariable Integer speciesId) {
        return feedingScheduleServices.getFeedingSchedulesBySpeciesId(speciesId);
    }
    @GetMapping("/get-all-feedSchedule-by-quantity/asc")
    public List<FeedingSchedule> getFeedingSchedulesByAscQuantity() {
        return feedingScheduleServices.getFeedingSchedulesByAscQuantity();
    }

    @GetMapping("/get-all-feedSchedule-by-quantity/desc")
    public List<FeedingSchedule> getFeedingSchedulesByDescQuantity() {
        return feedingScheduleServices.getFeedingSchedulesByDescQuantity();
    }

    @GetMapping("/get-feedSchedule-by-description/{keyword}")
    public List<FeedingSchedule> getFeedingSchedulebyKeywordDescription(@PathVariable String keyword) {
        return feedingScheduleServices.getFeedingScheduleByDescription(keyword);
    }

    @PostMapping("/add-feedingSchedule")
    public ResponseEntity<?> addFeedingSchedule(@RequestBody FeedingScheduleDto feedingScheduleDto) {
        String response = feedingScheduleServices.addFeedingSchedule(feedingScheduleDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/update-feedingSchedule")
    public ResponseEntity<?> updateFeedingSchedule(@RequestBody FeedingScheduleDto feedingScheduleDto) {
        String response = feedingScheduleServices.updateFeedingSchedule(feedingScheduleDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/remove-feedingSchedule/{feedScheduleId}")
    public ResponseEntity<?> removeFeedingSchedule(@PathVariable Integer feedScheduleId) {
        String response = feedingScheduleServices.removeFeedingSchedule(feedScheduleId);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

}
