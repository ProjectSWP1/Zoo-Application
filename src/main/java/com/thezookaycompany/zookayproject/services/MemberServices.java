package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Member;

import java.util.List;

public interface MemberServices {
    void addMember(AccountDto accountDto, MemberDto memberDto);

    Member updateMemberByPhoneNumber(String phoneNumber, Member updatedMember);
    List<Member> getAllMember();
    Member findMemberByPhoneNumber (String phoneNumber);


}
