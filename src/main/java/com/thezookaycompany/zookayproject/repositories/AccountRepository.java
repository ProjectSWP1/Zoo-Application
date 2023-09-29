package com.thezookaycompany.zookayproject.repositories;

import com.thezookaycompany.zookayproject.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@EnableJpaRepositories
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);


    @Query("SELECT a FROM Account a WHERE a.email = :email")
    Account findOneByEmail(@Param("email") String email);

    Account findAccountByEmail(String email);

    Optional<Account> findOneByEmailAndPassword(String email, String password);

    Account findByResetPwdToken (String token);


}
