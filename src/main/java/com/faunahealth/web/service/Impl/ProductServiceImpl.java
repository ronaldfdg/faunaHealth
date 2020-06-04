package com.faunahealth.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Product;
import com.faunahealth.web.repository.ProductRepository;
import com.faunahealth.web.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repositoryProduct;
	
	@Override
	public void save(Product product) {
		repositoryProduct.save(product);
	}
	
	@Override
	public List<Product> productsByKindAndSpecieAndName(int idProductKind, int idSpecie, String name, Pageable page){
		return repositoryProduct.productsByKindAndSpecieAndName(idProductKind, idSpecie, name, page);
	}
	
	@Override
	public List<Product> productsBySpecieAndName(int idSpecie, String name, Pageable page){
		return repositoryProduct.productsBySpecieAndName(idSpecie, name, page);
	}
	
	@Override
	public List<Product> productsByKindAndName(int idProductKind, String name, Pageable page){
		return repositoryProduct.productsByKindAndName(idProductKind, name, page);
	}
	
	@Override
	public List<Product> productsBySpecieAndKind(int idSpecie, int idProductKind, Pageable page){
		return repositoryProduct.productsBySpecieAndKind(idSpecie, idProductKind, page);
	}
	
	@Override
	public List<Product> productsByName(String name, Pageable page){
		return repositoryProduct.productsByName(name, page);
	}
	
	@Override
	public List<Product> findByIdIn(List<Integer> productsId){
		return repositoryProduct.findByIdIn(productsId);
	}
	
}
