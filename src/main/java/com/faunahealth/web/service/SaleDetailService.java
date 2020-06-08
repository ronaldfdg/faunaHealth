package com.faunahealth.web.service;

import java.util.List;

import com.faunahealth.web.entity.Product;
import com.faunahealth.web.entity.Sale;
import com.faunahealth.web.entity.SaleDetail;

public interface SaleDetailService {

	void processSaleDetails(List<Product> products, Integer[] amount, Sale sale);
	List<SaleDetail> findBySale_Id(int saleId);
	
}
