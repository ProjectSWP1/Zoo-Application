package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidCageException;
import com.thezookaycompany.zookayproject.model.dto.CageDto;
import com.thezookaycompany.zookayproject.model.entity.Cage;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.CageRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.CageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
