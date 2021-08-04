package com.lejian.laogang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LaogangApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaogangApplication.class, args);
	}

}
