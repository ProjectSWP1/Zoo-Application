package com.thezookaycompany.zookayproject.controller;

import com.thezookaycompany.zookayproject.model.Guest;
import com.thezookaycompany.zookayproject.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guests")

public class GuestController {
    @Autowired
    private GuestService guestService;

    @PostMapping("/add")
    public String add(@RequestBody Guest guest) {
        guestService.saveGuest(guest);
        return "New guest is added";
    }
}
