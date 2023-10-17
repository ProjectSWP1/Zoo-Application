package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.ZooAreaRepository;
import com.thezookaycompany.zookayproject.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberServices {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ZooAreaRepository zooAreRepository;
    @Override
    public void addMember(AccountDto accountDto, MemberDto memberDto) {
        Member member = new Member(
                accountDto.getPhoneNumber(),
                memberDto.getName(),
                accountDto.getEmail(),
                memberDto.getAddress(),
                0, // Removed age columns
                memberDto.getGender(),
                convertDateFormat(memberDto.getDob())
        );
        memberRepository.save(member);
    }

    private Date convertDateFormat(String dob) {

        // Create a SimpleDateFormat for the input format
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // Create a SimpleDateFormat for the output format
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the input string to a Date object
            Date date = inputDateFormat.parse(dob);

            // Format the Date object to a formatted String
            String formattedDateString = outputDateFormat.format(date);

            // Parse the formatted String back to a Date object
            Date formattedDate = outputDateFormat.parse(formattedDateString);

            return formattedDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> getAllMember() {
        return memberRepository.findAll();
    }


    @Override
    public Member findMemberByPhoneNumber(String phoneNumber) {
        return memberRepository.findMemberByPhoneNumber(phoneNumber);
    }

    // TODO: Remove Zoo Area out of this MemberService
    @Override
    public ZooArea findZooAreaByZooAreaID(String zooAreaId) {
        return zooAreRepository.findZooAreaByZooAreaId(zooAreaId);
    }

    @Override
    public List<ZooArea> findAllZooArea() {
        return zooAreRepository.findAll();
    }

    @Override
    public ZooArea findZooAreaByZooAreaDes(String description) {
        return zooAreRepository.findByDescriptionContainingIgnoreCase(description);
    }


    @Override
    public Member updateMemberByPhoneNumber(String phoneNumber, Member updatedMember) {
        Member existingMember = findMemberByPhoneNumber(phoneNumber);
        if (existingMember != null) {
            // Cập nhật thông tin của thành viên có số điện thoại cụ thể
            existingMember.setName(updatedMember.getName());
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setAddress(updatedMember.getAddress());
            existingMember.setAge(0);
            existingMember.setDob(updatedMember.getDob());
            existingMember.setGender(updatedMember.getGender());
            // Lưu thông tin cập nhật vào cơ sở dữ liệu
            return memberRepository.save(existingMember);
        }
        return null; // Trả về null nếu không tìm thấy thành viên
    }





}
