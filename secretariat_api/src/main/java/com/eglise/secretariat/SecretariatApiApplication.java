package com.eglise.secretariat;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

// scan du package maping :
@EntityScan(basePackages = {"com.eglise.secretariat", "com.eglise.model"})

public class SecretariatApiApplication {

	public static void main(String[] args) {

		// Charger les variables du fichier .env
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(SecretariatApiApplication.class, args);
	}

}
