package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidCageException;
import com.thezookaycompany.zookayproject.model.dto.*;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/trainer")
// Note that: Staff and Admin can use these functions of trainer, show I set the name 'ManageController'
public class ManageController {

    private final String SUCCESS_RESPONSE = "success";

    @Autowired
    private CageService cageService;

    @Autowired
    private ZooAreaService zooAreaService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AnimalFoodServices animalFoodServices;

    @Autowired
    private FeedingScheduleServices feedingScheduleServices;

    // TRUY XUẤT DỮ LIỆU: VIEW (get) //
    // Hàm này để truy xuất tìm Zoo Cage dựa trên Zoo Area ID
    @GetMapping("/get-cage/{zooAreaId}")
    public List<Cage> getCagesByZooArea(@PathVariable String zooAreaId) {

        ZooArea zooArea = zooAreaService.findZooAreaByZooAreaID(zooAreaId);
        return cageService.listCagesByZooArea(zooArea);
    }

    // Lấy tất cả cage dựa trên Zoo Area

    // Hàm này để lấy tất cả cage đang có
    @GetMapping("/get-cage")
    public List<Cage> getAllCages() {
        return cageService.getAllCages();
    }

    // Lấy zoo area hiện đang có để frontend làm thẻ select khi chuẩn bị tạo cage
    @GetMapping("/get-zoo-area")
    public List<ZooArea> getAllZooArea() {
        return zooAreaService.findAllZooArea();
    }

    // Truy xuất dữ liệu dựa vào keyword description (search keyword)
    @GetMapping("/get-cage-desc/{keyword}")
    public List<Cage> getCagesByDescription(@PathVariable String keyword) {
        return cageService.getCagesByDescriptionKeyword(keyword);
    }

    // Hàm này để lấy tất cả cage dựa vào capacity TĂNG DẦN
    @GetMapping("/get-cage/ascending")
    public List<Cage> getCagesByCapacityAscending() {
        return cageService.getCagesByCapacityAscending();
    }

    // Hàm này để lấy tất cả cage dựa vào capacity GIẢM DẦN
    @GetMapping("/get-cage/descending")
    public List<Cage> getCagesByCapacityDescending() {
        return cageService.getCagesByCapacityDescending();
    }
    // Tạo thêm Chuồng: CREATE //

    // Hàm này để tạo thêm chuồng mới dựa vào Zoo Area
    @PostMapping("/create-cage")
    public ResponseEntity<?> createCage(@RequestBody CageDto cageDto) {
        Cage cage = null;
        try {
            cage = cageService.createCage(cageDto);
        } catch (InvalidCageException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Added Cage " + cageDto.getCageID() + " successfully.");
    }

    // Update Cage: UPDATE //

    @PutMapping("/update-cage")
    public ResponseEntity<String> updateCage(@RequestBody CageDto cageDto) {
        String updateResponse = cageService.updateCage(cageDto);

        if (updateResponse.contains(SUCCESS_RESPONSE)) {
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
            String response = cageService.removeCage(cageId);
            if(response.contains(SUCCESS_RESPONSE)) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
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

    @PostMapping("/create-animal")
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

        if (updateResponse.contains(SUCCESS_RESPONSE)) {
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
        if (updateResponse.contains(SUCCESS_RESPONSE)) {
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

    @GetMapping("/get-all-animalSpecies")
    public List<AnimalSpecies> getAllAnimalSpecies() {
        return animalService.getAllAnimalSpecies();
    }

    @GetMapping("/get-animal/height-ascending")
    public List<Animal> getAnimalsByHeightAscending() {
        return animalService.findAllByHeightAsc();
    }
    @GetMapping("/get-animal/height-descending")
    public List<Animal> getAnimalsByHeightDescending() {
        return animalService.findAllByHeightDesc();
    }
    @GetMapping("/get-animal/weight-ascending")
    public List<Animal> getAnimalsByWeightAscending() {
        return animalService.findAllByWeightAsc();
    }
    @GetMapping("/get-animal/weight-descending")
    public List<Animal> getAnimalsByWeightDescending() {
        return animalService.findAllByWeightDesc();
    }
    @GetMapping("/get-animal/age-ascending")
    public List<Animal> getAnimalsByAgeAscending() {
        return animalService.findAllByAgeAsc();
    }
    @GetMapping("/get-animal/age-descending")
    public List<Animal> getAnimalsByAgeDescending() {
        return animalService.findAllByAgeDesc();
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

    @GetMapping("/get-employee-by/{email}")
    public Employees getEmployeeByEmail(@PathVariable String email) {
        return employeeService.getEmployeeByEmail(email);
    }

    @PostMapping("/{employeeId}/upload-qualification")
    public ResponseEntity<String> uploadQualification(
            @PathVariable int employeeId,
            @RequestParam("qualificationFile") MultipartFile qualificationFile) {
        try {
            employeeService.uploadQualificationImage(employeeId, qualificationFile);
            return ResponseEntity.ok("Qualification image uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading qualification image: " + e.getMessage());
        }
    }
    @GetMapping("/{employeeId}/qualification-image")
    public ResponseEntity<byte[]> getQualificationImage(@PathVariable int employeeId) {
        byte[] image = employeeService.getQualificationImageById(employeeId);
        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{employeeId}/delete-qualification")
    public ResponseEntity<String> deleteQualificationImage(@PathVariable int employeeId) {
        try {
            employeeService.deleteQualificationImage(employeeId);
            return ResponseEntity.ok("Qualification image deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting qualification image: " + e.getMessage());
        }
    }
    @PutMapping("/{employeeId}/update-qualification")
    public ResponseEntity<String> updateQualificationImage(
            @PathVariable int employeeId,
            @RequestParam("newQualificationFile") MultipartFile newQualificationFile) {
        try {
            employeeService.updateQualificationImage(employeeId, newQualificationFile);
            return ResponseEntity.ok("Qualification image updated successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating qualification image: " + e.getMessage());
        }
    }
    @PutMapping("/update-profile")
    public ResponseEntity<String> updateEmployees(@RequestBody EmployeesDto employeesDto) {
        String response = employeeService.updateEmployees(employeesDto);
        if (response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
