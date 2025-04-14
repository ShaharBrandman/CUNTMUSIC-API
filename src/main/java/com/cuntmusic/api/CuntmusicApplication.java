package com.cuntmusic.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.cuntmusic.utils.Repos") //auto configure repo interfaces
@EntityScan(basePackages = "com.cuntmusic.utils.tables") //auto configure tables
public class CuntmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuntmusicApplication.class, args);
	}

}
