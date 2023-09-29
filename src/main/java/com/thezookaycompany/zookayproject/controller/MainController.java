package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/home")
public class MainController {

    @Autowired
    private EmployeesRepository employeesRepository;

    @RequestMapping("/hello")
    public String home() {
        return "hello there";
    }

    @PostMapping("/test")
    public Optional<Employees> getEmployees(@RequestBody Integer EmpID) {
        return employeesRepository.findById(EmpID);
    }
}
