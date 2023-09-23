package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String username);

    Optional<Account> findOneByEmailAndPassword(String email, String password);
}
