package com.thezookaycompany.zookayproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ZookayprojectApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZookayprojectApplication.class, args);
	}

}
