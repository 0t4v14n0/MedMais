package com.medMais;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.medMais")
public class MedMaisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedMaisApplication.class, args);
	}

}
