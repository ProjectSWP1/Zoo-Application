package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.services.MemberServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberServices {

    @Autowired
    private MemberRepository memberRepository;
    @Override
    public void addMember(AccountDto accountDto, MemberDto memberDto) {
        Member member = new Member(
                accountDto.getPhoneNumber(),
                memberDto.getName(),
                accountDto.getEmail(),
                memberDto.getAddress(),
                memberDto.getAge(),
                memberDto.getGender()
        );
        memberRepository.save(member);
    }
}
