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
		for(HistoryDetail historyDetail : repository.findHistoryDetailsPerClinicHistory(4))
			System.out.println(historyDetail);
	}*/
	
}
