package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voucher")
@CrossOrigin("*")
public class VoucherController {

    private final String SUCCESS_RESPONSE = "success";

    @Autowired
    private VoucherService voucherService;

    @GetMapping("/id/{voucherID}")
    public Voucher getVoucherByID(@PathVariable String voucherID) {
        return voucherService.findVoucherByID(voucherID);
    }

    // Áp dụng Voucher vào vé
    @PutMapping("/apply-to-ticket/{ticketId}/{voucherID}")
    public ResponseEntity<String> addVoucherToTicket(@PathVariable String voucherID, @PathVariable String ticketId) {
        String response = voucherService.applyVoucherToTicket(voucherID, ticketId);
        if(response.equals(SUCCESS_RESPONSE)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
