package com.faunahealth.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FaunaHealthApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(FaunaHealthApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		getPatients();
	}
	
	public void getPatients() {
		for(Patient patient : repository.findByClient_PrimaryLastNameContaining("Do"))
			System.out.println(patient);
	}*/

}
