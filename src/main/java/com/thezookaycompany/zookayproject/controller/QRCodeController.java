package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.dto.OrdersDto;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.services.OrdersService;
import com.thezookaycompany.zookayproject.utils.TicketQRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/";

    @Autowired
    private OrdersService ordersService;

    @PostMapping(value = "/generateAndDownloadQRCode/{width}/{height}")
    public void download(
            @RequestBody OrdersDto ordersDto,
            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        TicketQRCodeGenerator ticketQRCodeGenerator = new TicketQRCodeGenerator();
        Orders orders = ordersService.findOrdersByOrderID(ordersDto.getOrderID());
        if(orders != null) {
            ticketQRCodeGenerator.generateQRCodeImage(orders, width, height, QR_CODE_IMAGE_PATH);
        }
    }

    @PostMapping(value = "/generateQRCode/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(
            @RequestBody OrdersDto ordersDto,
            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        TicketQRCodeGenerator ticketQRCodeGenerator = new TicketQRCodeGenerator();
        Orders orders = ordersService.findOrdersByOrderID(ordersDto.getOrderID());
        if(orders != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ticketQRCodeGenerator.getQRCodeImage(orders, width, height));
        }
        return null;
    }
}