package com.faunahealth.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Sale;
import com.faunahealth.web.repository.SaleRepository;
import com.faunahealth.web.service.SaleService;

@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository repositorySale;
	
	@Override
	public void save(Sale sale) {
		repositorySale.save(sale);
	}
	
}
