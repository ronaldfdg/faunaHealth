package com.faunahealth.web.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Product;
import com.faunahealth.web.entity.Sale;
import com.faunahealth.web.entity.SaleDetail;
import com.faunahealth.web.repository.SaleDetailRepository;
import com.faunahealth.web.service.SaleDetailService;

@Service
public class SaleDetailServiceImpl implements SaleDetailService {

	@Autowired
	private SaleDetailRepository repositorySaleDetail;
	
	@Override
	public void processSaleDetails(List<Product> products, Integer[] amount, Sale sale) {
		
		List<SaleDetail> saleDetails = new ArrayList<>();
		
		for(int i = 0; i < products.size(); i++) {
			SaleDetail saleDetail = new SaleDetail();
			
			saleDetail.setProduct(products.get(i));
			saleDetail.setSale(sale);
			saleDetail.setAmount(amount[i]);
			
			double subtotal = 0;
			subtotal = saleDetail.getProduct().getSalePrice() * saleDetail.getAmount();
			saleDetail.setSubtotal(subtotal);
			
			saleDetails.add(saleDetail);
		}
		
		repositorySaleDetail.saveAll(saleDetails);
	}
	
	@Override
	public List<SaleDetail> findBySale_Id(int saleId){
		return repositorySaleDetail.findBySale_Id(saleId);
	}

}
