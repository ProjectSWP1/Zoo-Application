package com.thezookaycompany.zookayproject;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.Role;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ZookayprojectApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZookayprojectApplication.class, args);

	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, AccountRepository accountRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		return args -> {
//			Role adminRole = roleRepository.save(new Role("AD", "Admin"));
//			roleRepository.save(new Role("MB", "Member"));
//			roleRepository.save(new Role("ZT", "Trainer"));
//			roleRepository.save(new Role("ST", "Staff"));

			// Tạo account cho admin truy cập
			Member member = new Member("0909090909", "admin", "admin@official.zookay.com", "empty", 1, "UNDEFINED");
			memberRepository.save(member);
			Account accAdmin = new Account("admin", passwordEncoder.encode("123123"), "admin@official.zookay.com", member, new Role("AD", "Admin"));

			accountRepository.save(accAdmin);
		};
	}

}
