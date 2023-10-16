package com.thezookaycompany.zookayproject.controller;


import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.ZooNewsDto;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import com.thezookaycompany.zookayproject.services.ZooNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/staff")
@CrossOrigin
public class StaffController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ZooNewsService zooNewsService;

    @Autowired
    private TrainerScheduleService trainerScheduleService;

    @GetMapping("/")
    public String helloStaff() {
        return "Staff access";
    }

    @GetMapping("/view-trainer")
    public List<Account> getAllTrainer (){
        return accountRepository.findAllByRole("ZT");
    }

    @GetMapping("/view-trainer/schedule")
    public Set<TrainerScheduleWeekDays> getTrainerSchedule(@RequestParam int empId) {
        return trainerScheduleService.getTrainerSchedule(empId);
    }

    @PostMapping("/modify-trainer")
    public String updateAccountRole (@RequestBody AccountDto accountDto, @RequestParam String newRole){
        
        accountRepository.updateAccountRole(accountDto.getEmail(),newRole);

        return "Update successfully";
    }

    @GetMapping("/get-trainer-employees")
    public List<Employees> getTrainerEmployees() {
        return employeeService.getTrainerEmployees();
    }

    @PostMapping("/postnews")
    public ResponseEntity<String> postNews(@RequestBody ZooNewsDto zooNewsDto) {
        String updatedResponse = zooNewsService.postNews(zooNewsDto);

        if(updatedResponse.contains("success")) {
            return ResponseEntity.ok(updatedResponse);
        } else {
            return ResponseEntity.badRequest().body(updatedResponse);
        }
    }


}
