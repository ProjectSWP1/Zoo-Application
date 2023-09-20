package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
