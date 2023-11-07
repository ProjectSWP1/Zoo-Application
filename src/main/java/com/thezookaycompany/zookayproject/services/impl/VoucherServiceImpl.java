package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidAnimalException;
import com.thezookaycompany.zookayproject.exception.InvalidVoucherException;
import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.model.entity.Animal;
import com.thezookaycompany.zookayproject.model.entity.Ticket;
import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.repositories.TicketRepository;
import com.thezookaycompany.zookayproject.repositories.VoucherRepository;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service

public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    public String createVoucher(VoucherDto voucherDto) {
        if(voucherDto.getVoucherId() == null || voucherDto.getVoucherId().length() >= 6) {
            return "Voucher ID field is empty or the length is greater than 5 characters";
        }
        if(voucherRepository.existsById(voucherDto.getVoucherId())) {
            return "This voucher id has already existed";
        }
        if(!ticketRepository.existsById(voucherDto.getTicketId())) {
            return "This ticket id does not existed";
        }
        Voucher newVoucher = new Voucher();
        Ticket ticket1 = ticketRepository.getReferenceById(voucherDto.getTicketId());
        newVoucher.setVoucherId(voucherDto.getVoucherId());
        newVoucher.setTicket(ticket1);
        newVoucher.setCoupon(voucherDto.getCoupon());
        newVoucher.setDescription(voucherDto.getDescription());
        newVoucher.setExpirationDate(voucherDto.getExpirationDate());
        voucherRepository.save(newVoucher);
        return "New voucher has been added successfully";
    }

    @Override
    public String updateVoucher(VoucherDto voucherDto) {
        if (voucherDto.getVoucherId() == null || voucherDto.getVoucherId().length() >= 6) {
            return "Voucher ID field is empty or the length is greater than 5 characters";
        }
        Voucher existingVoucher = voucherRepository.findById(voucherDto.getVoucherId()).orElse(null);
        Ticket ticket1 = ticketRepository.getReferenceById(voucherDto.getTicketId());
        if (existingVoucher != null){
            //update
        existingVoucher.setVoucherId(voucherDto.getVoucherId());
        existingVoucher.setTicket(ticket1);
        existingVoucher.setCoupon(voucherDto.getCoupon());
        existingVoucher.setDescription(voucherDto.getDescription());
        existingVoucher.setExpirationDate(voucherDto.getExpirationDate());
        voucherRepository.save(existingVoucher);
        return "Voucher updated successfully";
    }else{
        return "Voucher not found with Id" + voucherDto.getVoucherId();
    }
    }

    @Override
    public String deleteVoucher(String id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new InvalidVoucherException("Not found this Voucher ID to delete."));
        voucherRepository.delete(voucher);
        return voucher.getVoucherId();
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher findVoucherByID(String voucherID) {
        return voucherRepository.findById(voucherID).orElse(null);
    }

    @Override
    public String applyVoucherToTicket(String voucherId, String ticketId) {
        if(voucherId == null || voucherId.isEmpty() || !voucherRepository.existsById(voucherId)) {
            return "Voucher ID is not found";
        }
        if(ticketId == null || ticketId.isEmpty() || !ticketRepository.existsById(ticketId)) {
            return "Ticket ID is not found";
        }
        Voucher voucher = voucherRepository.findById(voucherId).orElse(null);
        if(voucher == null) {
            return "Voucher is not found";
        }
        voucher.setTicket(ticketRepository.getReferenceById(ticketId));

        return "This voucher has been applied successfully";
    }
}
