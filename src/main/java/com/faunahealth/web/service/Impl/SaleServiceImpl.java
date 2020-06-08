package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Client;
import com.faunahealth.web.entity.Product;
import com.faunahealth.web.entity.Sale;
import com.faunahealth.web.repository.SaleRepository;
import com.faunahealth.web.service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository repositorySale;
	
	@Override
	public Sale processSale(Client client, String ticketNumber, double cash, List<Product> products, Integer[] amount) {
		
		Sale sale = new Sale();
		sale.setClient(client);
		sale.setTicketNumber(ticketNumber);
		sale.setCash(cash);
		
		double total = 0;
		for(int i = 0; i < products.size(); i++) 
			total += products.get(i).getSalePrice() * amount[i];
		sale.setTotal(total);
		
		double change = 0;
		change = cash - total;
		sale.setChange(change);
		
		repositorySale.save(sale);
		
		return sale;
	}
	
	@Override
	public Sale findById(int id) {
		Optional<Sale> optional = repositorySale.findById(id);
		if(optional.isPresent())
			return optional.get();
		return null;
	}
	
	@Override
	public List<Sale> findByClient(int clientId, Pageable page){
		return repositorySale.findByClient(clientId, page);
	}
	
	@Override
	public List<Sale> findByDate(Date startDate, Pageable page){
		return repositorySale.findByDate(startDate, page);
	}
	
	@Override
	public List<Sale> findBetweenDates(Date startDate, Date endDate, Pageable page){
		return repositorySale.findBetweenDates(startDate, endDate, page);
	}
	
}
