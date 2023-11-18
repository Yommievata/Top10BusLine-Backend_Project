package com.sbab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc //Come back to this later
public class BusTrafficApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusTrafficApplication.class, args);
	}

}
