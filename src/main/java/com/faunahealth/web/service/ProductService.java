package com.faunahealth.web.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.Product;

public interface ProductService {

	List<Product> findAll();
	List<Product> productsByKindAndSpecieAndName(String idProductKind, String idSpecie, String name, Pageable page);
	Product findById(int id);
	List<Product> findByIdIn(List<Integer> productsId);
	boolean existsById(int id);
	void deleteById(int id) throws Exception;
	void save(Product product);
	
}
