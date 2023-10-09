package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.exception.InvalidTicketException;
import com.thezookaycompany.zookayproject.model.dto.TicketDto;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
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
    private AccountRepository accountRepository;

    @GetMapping("/")
    public String adminAccess() {
        return "Admin accessed";
    }

    @GetMapping("/getAccount")
    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    //Tìm Ticket dựa vào TicketID
    @GetMapping("/get-ticket/{ticketId}")
    Ticket findTicketByTicketID(@PathVariable("ticketId") String ticketId) {
        return ticketService.findTicketByTicketID(ticketId);
    }
    //Tìm tất cả ticket đang có
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




}
