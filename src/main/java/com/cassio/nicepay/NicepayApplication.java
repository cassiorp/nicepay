package com.cassio.nicepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class NicepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(NicepayApplication.class, args);
	}

}
