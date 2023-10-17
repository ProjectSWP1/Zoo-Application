package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.EmployeesDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.EmployeesRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private ZooAreaRepository zooAreaRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }

    @Override
    public List<Employees> getTrainerEmployees() {
        return employeesRepository.findAllZooTrainers();
    }

    @Override
    public List<Employees> getEmployeesManageZooArea(String zooAreaID) {
        ZooArea zooArea = zooAreaRepository.findById(zooAreaID).orElse(null);
        if(zooArea != null) {
            List<Employees> employeesList = employeesRepository.findEmployeesByZooArea(zooArea);
            if(employeesList != null) {
                return employeesList;
            }
        }
        return null;
    }



    @Override
    public List<Employees> getActiveEmployees() {
        return employeesRepository.findEmployeesByActiveIsTrue();
    }

    @Override
    public String addEmployees(EmployeesDto employeesDto) {
        if(!isValid(employeesDto)) {
            return "Invalid data, please check the input again.";
        }

        if(employeesRepository.findById(employeesDto.getManagedByEmpID()).isEmpty()) {
            return "Not found managed Employee ID";
        }

        if(zooAreaRepository.findById(employeesDto.getZoo_areaID()).isEmpty()) {
            return "Not found zoo area id";
        }

        Set<Employees> employeesSet = new HashSet<>();
        employeesSet.add(employeesRepository.findById(employeesDto.getManagedByEmpID()).get());

        Date doB;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            doB = simpleDateFormat.parse(employeesDto.getDob());
        } catch (ParseException e) {
            return "Cannot parse date format " + employeesDto.getDob() + ", please follow yyyy-MM-ddd format";
        }

        Account acc = accountRepository.findOneByEmail(employeesDto.getEmail());
        if(acc == null) {
            return "The email does not exist in Account";
        }

        if(employeesRepository.findEmployeesByEmail(acc) != null) {
            return "This email is being used by Employees " + acc.getEmail();
        }

        Employees employees = new Employees();
        employees.setName(employeesDto.getName());
        employees.setAddress(employeesDto.getAddress());
        employees.setActive(true);
        employees.setDoB(doB);
        employees.setPhoneNumber(employeesDto.getPhone_number());
        employees.setEmail(accountRepository.findOneByEmail(employeesDto.getEmail()));
        employees.setZooArea(zooAreaRepository.getZooAreaByZooAreaId(employeesDto.getZoo_areaID()));

        acc.setActive(true);
        System.out.println("The account " + employeesDto.getEmail() + "authorized as role '"+ acc.getRole() +"', you should modify it in Account management");

        employeesRepository.save(employees);
        return "New employees " + employeesDto.getName() + " has been added successfully";
    }

    @Override
    public String deactivateEmployees(Integer empID) {
        Employees employees = employeesRepository.findById(empID).orElse(null);
        if(employees != null) {
            if(!employees.isActive()) {
                return "Employees " + empID + " has already been disabled";
            }
            employees.setActive(false);
            // Check employees đang có job Trainer Schedule không
            // TODO: thằng nhân tự thêm vào đi

            employeesRepository.save(employees);
            return "Employees " + empID + " has been disabled";
        }
        return "Failed to retrieve the Employee ID";
    }

    @Override
    public String updateEmployees(EmployeesDto employeesDto) {
        Employees employees = employeesRepository.findById(employeesDto.getEmpID()).orElse(null);
        if(employees == null) {
            return "No employee is found";
        }

        if(!isValid(employeesDto)) {
            return "Invalid data to update, please check the input again";
        }
        if(employeesRepository.findById(employeesDto.getManagedByEmpID()).isEmpty()) {
            return "Not found managed Employee ID";
        }

        if(zooAreaRepository.findById(employeesDto.getZoo_areaID()).isEmpty()) {
            return "Not found zoo area id";
        }

        Set<Employees> employeesSet = new HashSet<>();
        employeesSet.add(employeesRepository.findById(employeesDto.getManagedByEmpID()).get());

        Date doB;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            doB = simpleDateFormat.parse(employeesDto.getDob());
        } catch (ParseException e) {
            return "Cannot parse date format " + employeesDto.getDob() + ", please follow yyyy-MM-ddd format";
        }

        Account acc = accountRepository.findOneByEmail(employeesDto.getEmail());
        if(acc == null) {
            return "The email does not exist in Account";
        }

        if(employeesRepository.findEmployeesByEmail(acc) != null) {
            return "This email is being used by Employees " + acc.getEmail();
        }

        employees.setName(employeesDto.getName());
        employees.setAddress(employeesDto.getAddress());
        employees.setActive(employeesDto.isActive());
        employees.setDoB(doB);
        employees.setPhoneNumber(employeesDto.getPhone_number());
        employees.setEmail(accountRepository.findOneByEmail(employeesDto.getEmail()));
        employees.setZooArea(zooAreaRepository.getZooAreaByZooAreaId(employeesDto.getZoo_areaID()));

        employeesRepository.save(employees);


        return "Employee " + employeesDto.getEmail() +" has been updated successfully";
    }

    @Override
    public void uploadQualificationImage(int employeeId, MultipartFile qualificationFile) throws IOException {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (qualificationFile != null && !qualificationFile.isEmpty()) {
            byte[] qualificationData = qualificationFile.getBytes();
            employee.setQualification(qualificationData);
            employeesRepository.save(employee);
        }
    }

    @Override
    public byte[] getQualificationImageById(int employeeId) {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            return employee.getQualification();
        } else {
            return null;
        }
    }

    @Override
    public void deleteQualificationImage(int employeeId) {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            employee.setQualification(null); // Delete the image by assigning null value
            employeesRepository.save(employee);
        }
    }

    @Override
    public void updateQualificationImage(int employeeId, MultipartFile newQualificationFile) throws IOException {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            if (newQualificationFile != null && !newQualificationFile.isEmpty()) {
                byte[] newQualificationData = newQualificationFile.getBytes();
                employee.setQualification(newQualificationData); // Update new image
                employeesRepository.save(employee);
            }
        }
    }

    private boolean isValid(EmployeesDto employeesDto) {
        if(employeesDto.getName().isEmpty() || employeesDto.getName() == null || employeesDto.getName().length() > 30) {
            return false;
        }

        if(employeesDto.getAddress().isEmpty() || employeesDto.getAddress() == null || employeesDto.getAddress().length() > 255) {
            return false;
        }

        if(!employeesDto.getEmail().contains("@")) {
            return false;
        }

        if(!isValidPhoneNumber(employeesDto.getPhone_number())) {
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "0[0-9]{9}";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}
