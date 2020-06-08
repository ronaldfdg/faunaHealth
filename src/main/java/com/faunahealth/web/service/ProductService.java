package com.faunahealth.web.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.faunahealth.web.entity.Product;

public interface ProductService {

	List<Product> findAll();
	List<Product> productsByKindAndSpecieAndName(int idProductKind, int idSpecie, String name, Pageable page);
	List<Product> productsBySpecieAndName(int idSpecie, String name, Pageable page);
	List<Product> productsByKindAndName(int idProductKind, String name, Pageable page);
	List<Product> productsBySpecieAndKind(int idSpecie, int idProductKind, Pageable page);
	List<Product> productsByName(String name, Pageable page);
	List<Product> findByIdIn(List<Integer> productsId);
	void save(Product product);
	
}
