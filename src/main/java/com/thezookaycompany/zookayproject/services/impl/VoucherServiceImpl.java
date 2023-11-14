package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.exception.InvalidVoucherException;
import com.thezookaycompany.zookayproject.model.dto.VoucherDto;
import com.thezookaycompany.zookayproject.model.entity.Orders;
import com.thezookaycompany.zookayproject.model.entity.Voucher;
import com.thezookaycompany.zookayproject.repositories.OrdersRepository;
import com.thezookaycompany.zookayproject.repositories.VoucherRepository;
import com.thezookaycompany.zookayproject.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private OrdersRepository ordersRepository;
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
        if(!ordersRepository.existsById(voucherDto.getOrderId())) {
            return "This order id does not existed";
        }
        Voucher newVoucher = new Voucher();
        Orders order1 = ordersRepository.getReferenceById(voucherDto.getOrderId());
        newVoucher.setVoucherId(voucherDto.getVoucherId());
        newVoucher.setOrder(order1);
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
        Orders order1 = ordersRepository.getReferenceById(voucherDto.getOrderId());
        if (existingVoucher != null){
            //update
        existingVoucher.setVoucherId(voucherDto.getVoucherId());
        existingVoucher.setOrder(order1);
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
    public String applyVoucherToTicket(String voucherID, String ticketID) {
        return null;
    }


}
