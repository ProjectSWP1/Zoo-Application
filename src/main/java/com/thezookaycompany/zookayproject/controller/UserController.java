package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.EmpNotFoundException;
import com.thezookaycompany.zookayproject.model.Account;
import com.thezookaycompany.zookayproject.model.Employees;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @PostMapping("/membership/save")
    public String newUser(@RequestBody Account acc) {
        Long empId = Long.valueOf(acc.getEmpId());
        Employees e = getEmployeesID(empId);
        if(e != null) {
            accountRepository.save(acc);
            return "New user has been added";
        }
        return "User cannot be added";
    }

    @GetMapping("/membership/{id}")
    public Employees getEmployeesID(@PathVariable Long id) {
        return employeesRepository.findById(id).orElseThrow(() -> new EmpNotFoundException(id));
    }
}
