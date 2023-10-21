package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidTicketException;
import com.thezookaycompany.zookayproject.exception.InvalidVoucherException;
import com.thezookaycompany.zookayproject.model.dto.*;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Employees;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.services.AccountService;
import com.thezookaycompany.zookayproject.services.EmployeeService;
import com.thezookaycompany.zookayproject.services.TicketService;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    private final String SUCCESS_RESPONSE = "success";
    @Autowired
    private TicketService ticketService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherService voucherService;

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
            return ResponseEntity.badRequest().body("Cannot assign this account to this role. Please check the Employee has this email account or not.");
        }
    }

    // Tạo account dành cho Admin
    /*
        @params accountDto, memberDto, role

        @return String

        Note: DB Employee chỉnh cột Zoo Area ID có thể null (nullable = true)
    */
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@RequestBody RequestWrapper requestWrapper, @RequestParam String roleId) {
        String response = accountService.admin_addAccount(requestWrapper.getAccountDto(), requestWrapper.getMemberDto(), roleId);
        if(response.contains("success")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/delete-account/{email}")
    public ResponseEntity<?> removeAccount(@PathVariable String email) {
        String response = accountService.removeAccount(email);
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

    @GetMapping("/get-ticket")
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTicket();
    }
    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự TĂNG DẦN//
    @GetMapping("/get-ticket/ascending")
    public List<Ticket> getTicketByTicketPriceAscending() {
        return ticketService.findAllByTicketPriceAsc();
    }

    //Hàm này lấy tất cả Ticket dựa vào Price theo thứ tự GIẢM DẦN//
    @GetMapping("/get-ticket/descending")
    public List<Ticket> getTicketByTicketPriceDescending() {
        return ticketService.findAllByTicketPriceDesc();
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

        if (response.contains(SUCCESS_RESPONSE)) {
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
    //Create Voucher with Voucher ID generated on FE
    @PostMapping("/create-voucher")
    public ResponseEntity<String> createAnimalVoucher(@RequestBody VoucherDto voucherDto) {
        String response = voucherService.createVoucher(voucherDto);
        if(response.contains(SUCCESS_RESPONSE)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.badRequest().body(response);
    }
    //Update ticketID on Voucher
    @PutMapping("/update-voucher")
    public ResponseEntity<?> updateVoucher(@RequestBody VoucherDto voucherDto) {
        String updateResponse = voucherService.updateVoucher(voucherDto);

        if (updateResponse.startsWith("Voucher updated successfully.")) {
            return ResponseEntity.ok(updateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
    }
    @DeleteMapping("/delete-voucher/{voucherId}")
    public ResponseEntity<String> removeVoucher(@PathVariable String voucherId) {
        try {
            String deletedVoucherId = voucherService.deleteVoucher(voucherId);
            return ResponseEntity.ok("Voucher removed successfully with id: " + deletedVoucherId);
        } catch (InvalidVoucherException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voucher not found with ID: " + voucherId);
        }
    }
}
