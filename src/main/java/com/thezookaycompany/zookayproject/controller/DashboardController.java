package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ZooAreaService zooAreaService;

    @Autowired
    private CageService cageService;

    @Autowired
    private FeedingScheduleServices feedingScheduleServices;

    // Đếm số động vật hiện đang có trong sở thú
    @GetMapping("/animals")
    public ResponseEntity<?> countAnimals() {
        return ResponseEntity.ok(animalService.countAnimals());
    }
    // Đếm số employees hiện tại đang làm việc tại Zoo (?active = 1)
    @GetMapping("/employees")
    public ResponseEntity<?> countEmployees() {
        return ResponseEntity.ok(employeeService.countEmployees());
    }

    // Đếm số động vật loài đang có
    @GetMapping("/species")
    public ResponseEntity<?> countSpecies() {
        return ResponseEntity.ok(animalService.countAnimalSpecies());
    }

    // Đếm các khu vực đang có trong sở thú
    @GetMapping("/zoo-areas")
    public ResponseEntity<?> countZooAreas() {
        return ResponseEntity.ok(zooAreaService.countZooAreas());
    }

    // Đếm các chuồng hiện đang có trong sở thú
    @GetMapping("/cages")
    public ResponseEntity<?> countCages() {
        return ResponseEntity.ok(cageService.countCages());
    }

    // Đếm số lịch cho ăn đang có trong sở thú
    @GetMapping("/feeding-schedules")
    public ResponseEntity<?> countFeedingSchedules() {
        return ResponseEntity.ok(feedingScheduleServices.countFeedingSchedules());
    }
}
