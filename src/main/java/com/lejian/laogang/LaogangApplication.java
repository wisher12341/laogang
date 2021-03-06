package com.lejian.laogang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
public class LaogangApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaogangApplication.class, args);
	}

}
