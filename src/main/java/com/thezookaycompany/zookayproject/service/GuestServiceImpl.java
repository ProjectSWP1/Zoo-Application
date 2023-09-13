package com.thezookaycompany.zookayproject.service;

import com.thezookaycompany.zookayproject.model.Guest;
import com.thezookaycompany.zookayproject.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestRepository guestRepository;
    @Override
    public Guest saveGuest(Guest guest) {
        return null;
    }
}
