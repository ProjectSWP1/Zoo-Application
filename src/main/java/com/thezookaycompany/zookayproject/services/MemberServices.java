package com.thezookaycompany.zookayproject.services;

import com.thezookaycompany.zookayproject.model.dto.AccountDto;
import com.thezookaycompany.zookayproject.model.dto.MemberDto;

public interface MemberServices {
    void addMember(AccountDto accountDto, MemberDto memberDto);
}
