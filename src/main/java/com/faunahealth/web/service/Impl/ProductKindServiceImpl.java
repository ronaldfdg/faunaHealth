package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.ProductKind;
import com.faunahealth.web.repository.ProductKindRepository;
import com.faunahealth.web.service.ProductKindService;

@Service
public class ProductKindServiceImpl implements ProductKindService {

	@Autowired
	private ProductKindRepository repositoryProductKind;
	
	@Override
	public List<ProductKind> kindsOfProduct() {
		return repositoryProductKind.findAll(Sort.by("name"));
	}

}
