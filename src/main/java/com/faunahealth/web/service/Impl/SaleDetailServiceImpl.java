package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.SaleDetail;
import com.faunahealth.web.repository.SaleDetailRepository;
import com.faunahealth.web.service.SaleDetailService;

@Service
public class SaleDetailServiceImpl implements SaleDetailService {

	@Autowired
	private SaleDetailRepository repositorySaleDetail;
	
	@Override
	public void save(List<SaleDetail> saleDetails) {
		repositorySaleDetail.saveAll(saleDetails);
	}

}
