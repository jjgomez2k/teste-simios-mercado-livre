package com.jjgomez2k.testesimios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class TesteSimiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteSimiosApplication.class, args);
	}

}