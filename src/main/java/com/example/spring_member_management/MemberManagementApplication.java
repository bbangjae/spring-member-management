package com.example.spring_member_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MemberManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberManagementApplication.class, args);
	}

}
