package com.thezookaycompany.zookayproject.controller;


import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.TrainerScheduleDto;
import com.thezookaycompany.zookayproject.model.dto.ZooNewsDto;
import com.thezookaycompany.zookayproject.model.entity.*;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import com.thezookaycompany.zookayproject.services.TrainerScheduleService;
import com.thezookaycompany.zookayproject.services.ZooNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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


    //chỉ trả về accountDto và security
    @GetMapping("/view-trainer")
    public List<Account> getAllTrainer (){
        return accountRepository.findAllByRole("ZT");
    }


    @PostMapping("/modify-trainer")
    public String updateAccountRole (@RequestBody AccountDto accountDto, @RequestParam String newRole){

        accountRepository.updateAccountRole(accountDto.getEmail(),newRole);

        return "Update successfully";
    }

    // trả về tất cả cái j có relation với employee này (ZooArea, schedule, account,..)
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



    //view all information about trainer include workday(mon,tues,wed,..)
    // 1 trainer thì có nhiều trainer schedule
    // 1 cái trainerSchedule thì chứa thông tin của 1 workday
    @GetMapping("/view-trainer/schedule")
    public Set<TrainerScheduleWeekDays> getTrainerSchedule(@RequestParam int empId) {
        return trainerScheduleService.getTrainerSchedule(empId);
    }

    // create trainer schedule
    @PostMapping("/assign-trainer-schedule")
    public ResponseEntity<String> createTrainerSchedule(@RequestBody TrainerScheduleDto trainerScheduleDto){
        String message =trainerScheduleService.CreateTrainerSchedule(trainerScheduleDto);
        if(message.contains("success")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }


}
