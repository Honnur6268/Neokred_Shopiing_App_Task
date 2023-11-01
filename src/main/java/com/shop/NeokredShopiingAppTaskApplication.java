package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeokredShopiingAppTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeokredShopiingAppTaskApplication.class, args);
		System.out.println("Connected to Db");
	}

}
