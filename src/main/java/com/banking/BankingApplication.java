package com.banking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan({ "com.banking" })
//public class BankingApplication extends SpringBootServletInitializer {
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(BankingApplication.class);
//	}
}
