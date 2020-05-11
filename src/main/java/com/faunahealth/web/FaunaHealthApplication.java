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
		getYear();
	}
	
	public void getYear() {
		Patient patient = repository.findById(4).get();
		System.out.println(patient.getBirthday());
		System.out.println(Utileria.getYears(patient.getBirthday(), new Date()));
	}*/

}
