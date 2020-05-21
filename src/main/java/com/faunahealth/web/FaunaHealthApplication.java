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
	
	//repository.findOperationsByPatientAndDates("Ro", "Do", firstDate, secondDate, Sort.by("operationDate").descending())
	//repository.findOperationsByDate(secondDate, Sort.by("operationDate"))
	
	public void getYear() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date firstDate = null;
		Date secondDate = null;
		
		try {
			firstDate = dateFormat.parse("15-05-2020");
			secondDate = dateFormat.parse("20-05-2020");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		for(OperationDetail operation : repository.findOperationsByPatientLastNameAndDate("Do", secondDate, Sort.by("operationDate")))
			System.out.println(operation);
	}*/
	
}
