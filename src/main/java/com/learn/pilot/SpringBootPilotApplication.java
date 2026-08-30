package com.learn.pilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application ka entry point.
 *
 * {@code @SpringBootApplication} teen cheezon ko combine karta hai:
 * 1. @Configuration — ye class beans define kar sakti hai
 * 2. @EnableAutoConfiguration — classpath pe jo libraries hain unke hisaab se setup
 * 3. @ComponentScan — isi package aur sub-packages me @Component/@Service/@RestController dhoondhta hai
 */
@SpringBootApplication
public class SpringBootPilotApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPilotApplication.class, args);
	}
}
