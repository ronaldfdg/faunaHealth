package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.entity.Product;
import com.faunahealth.web.entity.Sale;

public interface SaleService {
	
	List<Sale> findByClient(int clientId, Pageable page);
	List<Sale> findByDate(Date startDate, Pageable page);
	List<Sale> findBetweenDates(Date startDate, Date endDate, Pageable page);
	Sale processSale(Client client, String ticketNumber, double cash, List<Product> products, Integer[] amount);
	Sale findById(int id);
	
}
