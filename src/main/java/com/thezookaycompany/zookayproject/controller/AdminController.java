package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidTicketException;
import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.EmployeesDto;
import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import com.thezookaycompany.zookayproject.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/")
    public String adminAccess() {
        return "Admin accessed";
    }

    @GetMapping("/getAccount")
    public List<Account> getAllAccount(){
        return accountService.getAllAccount();
    }

    // Lấy tất cả account đang inactive (active = 0)
    @GetMapping("/getInactiveAccount")
    public List<Account> getAllInactiveAccount() {
        return accountService.getAllInactiveAccount();
    }

    @PutMapping("/assignRole")
    public ResponseEntity<?> assignRoleToAccount(@RequestBody AccountDto accountDto, @RequestParam String roleId) {
        boolean isSuccess = accountService.assignRoleToAccount(accountDto, roleId);
        if(isSuccess) {
            return ResponseEntity.ok("The account successfully assigned to Role " + roleId+".");
        } else {
            return ResponseEntity.badRequest().body("Something went wrong, please try again.");
        }
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<?> removeAccount(@RequestBody AccountDto accountDto) {
        String response = accountService.removeAccount(accountDto.getEmail());
        if(response.contains("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/deactivate-account/{email}")
    public ResponseEntity<?> deactivateAccount(@PathVariable String email) {
        String response = accountService.deactivateAccount(email);
        if(response.contains("success")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Tìm Ticket dựa vào TicketID
    @GetMapping("/get-ticket/{ticketId}")
    Ticket findTicketByTicketID(@PathVariable("ticketId") String ticketId) {
        return ticketService.findTicketByTicketID(ticketId);
    }
    //Tìm tất cả ticket đang có

    // TODO: Clean code > chuyển toàn bộ ticket repository sang ticket service
    @GetMapping("/get-ticket")
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự TĂNG DẦN//
    @GetMapping("/get-ticket/ascending")
    public List<Ticket> getTicketByTicketPriceAscending() {
        return ticketRepository.findAllByTicketPriceAsc();
    }

    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự GIẢM DẦN//
    @GetMapping("/get-ticket/descending")
    public List<Ticket> getTicketByTicketPriceDescending() {
        return ticketRepository.findAllByTicketPriceDesc();
    }
    //Hàm này tạo Ticket mới : CREATE//
    @PostMapping("/create-ticket")
    public Ticket createTicket(@RequestBody TicketDto ticketDto) {
        return ticketService.createTicket(ticketDto);
    }
    //Hàm này Update Ticket : UPDATE//
    @PutMapping("/update-ticket")
    public ResponseEntity<String> updateTicket(@RequestBody TicketDto ticketDto) {
        String updateResponse = ticketService.updateTicket(ticketDto);

        if (updateResponse.startsWith("Ticket updated successfully.")) {
            return ResponseEntity.ok(updateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }
    //Hàm này xóa Ticket : REMOVE//
    @DeleteMapping("/remove-ticket/{ticketId}")
    public ResponseEntity<String> removeTicket(@PathVariable String ticketId) {
        try {
            String deletedTicketId = ticketService.removeTicket(ticketId);
            return ResponseEntity.ok("Deleted Ticket id: " + deletedTicketId);
        } catch (InvalidTicketException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found with ID: " + ticketId);
        }
    }

    // Employess Manager

    @Autowired
    private EmployeeService employeeService;

    // Lấy tất cả Employees kể cả đã nghỉ việc (CD: active= 1 & active= 0)

    @GetMapping("/getAllEmployees")
    public List<Employees> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Lấy tất cả Employees đang làm việc (CD: active= 1)
    @GetMapping("/getActiveEmployees")
    public List<Employees> getActiveEmployees() {
        return employeeService.getActiveEmployees();
    }

    @GetMapping("/employeesByZooArea/{zooAreaID}")
    public ResponseEntity<?> getEmployeesByZooArea(@PathVariable String zooAreaID) {
        List<Employees> employeesList = employeeService.getEmployeesManageZooArea(zooAreaID);

        if (employeesList != null && !employeesList.isEmpty()) {
            return ResponseEntity.ok(employeesList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Tạo 1 employees mới
    @PostMapping("/createEmployees")
    public ResponseEntity<?> createEmployees(@RequestBody EmployeesDto employeesDto) {
        String response = employeeService.addEmployees(employeesDto);

        if (response.contains("success")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Deactive 1 employee
    @PutMapping("/deactivateEmployees/{empID}")
    public ResponseEntity<String> deactivateEmployees(@PathVariable Integer empID) {
        String response = employeeService.deactivateEmployees(empID);

        if (response.contains("disabled")) {
            // If the response indicates successful deactivation, return an HTTP 200 (OK) status.
            return ResponseEntity.ok(response);
        } else {
            // If there was an issue or employee not found, return an HTTP 404 (Not Found) status.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/updateEmployees")
    public ResponseEntity<String> updateEmployees(@RequestBody EmployeesDto employeesDto) {
        String response = employeeService.updateEmployees(employeesDto);

        if (response.contains("success")) {
            // If the response indicates successful update, return an HTTP 200 (OK) status.
            return ResponseEntity.ok(response);
        } else {
            // If there was an issue with the update, return an HTTP 400 (Bad Request) status.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }





}
