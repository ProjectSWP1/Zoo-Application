package com.thezookaycompany.zookayproject;

import com.stripe.Stripe;
import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.model.entity.Member;
import com.thezookaycompany.zookayproject.model.entity.Role;
import com.thezookaycompany.zookayproject.model.entity.WeekDays;
import com.thezookaycompany.zookayproject.repositories.AccountRepository;
import com.thezookaycompany.zookayproject.repositories.MemberRepository;
import com.thezookaycompany.zookayproject.repositories.RoleRepository;
import com.thezookaycompany.zookayproject.repositories.WeekDaysRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.List;

@SpringBootApplication
public class ZookayprojectApplication {

	@Value("${stripe.api.key}")
	private String secretKey;

	@PostConstruct
	public void setup (){
		Stripe.apiKey = secretKey;
	}

	public static void main(String[] args) {

		SpringApplication.run(ZookayprojectApplication.class, args);

	}
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, AccountRepository accountRepository, MemberRepository memberRepository, PasswordEncoder passwordEncoder, WeekDaysRepository weekDaysRepository) {
		return args -> {
			if(!weekDaysRepository.existsById(2)){
				WeekDays weekDays = weekDaysRepository.save(new WeekDays(2,"Monday"));
				weekDaysRepository.save(new WeekDays(3,"Tuesday"));
				weekDaysRepository.save(new WeekDays(4,"Wednesday"));
				weekDaysRepository.save(new WeekDays(5,"Thursday"));
				weekDaysRepository.save(new WeekDays(6,"Friday"));
				weekDaysRepository.save(new WeekDays(7,"Saturday"));
				weekDaysRepository.save(new WeekDays(8,"Sunday"));
			}
//			Role adminRole = roleRepository.save(new Role("AD", "Admin"));
//			roleRepository.save(new Role("MB", "Member"));
//			roleRepository.save(new Role("ZT", "Trainer"));
//			roleRepository.save(new Role("ST", "Staff"));

			// Tạo account cho admin truy cập
//			Member member = new Member("0909090909", "admin", "admin@official.zookay.com", "empty", 1, "UNDEFINED", Date.valueOf("1994-04-25"));
//			memberRepository.save(member);
//			Account accAdmin = new Account(
//					"admin",
//					passwordEncoder.encode("123123"),
//					"admin@official.zookay.com",
//					new Role("AD", "Admin"),
//					true,
//					member
//			);
//
//			accountRepository.save(accAdmin);
		};
	}

}
