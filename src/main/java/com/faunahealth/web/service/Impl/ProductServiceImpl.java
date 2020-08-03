	package com.faunahealth.web.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Product;
import com.faunahealth.web.repository.ProductRepository;
import com.faunahealth.web.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repositoryProduct;
	
	@Override
	public List<Product> findAll(){
		return repositoryProduct.findAll(Sort.by("name"));
	}
	
	@Override
	public void save(Product product) {
		repositoryProduct.save(product);
	}
	
	@Override
	public List<Product> productsByKindAndSpecieAndName(String idProductKind, String idSpecie, String name, Pageable page){
		
		if(name != null) {
			   
			if(idSpecie != null && idProductKind != null) {
				return repositoryProduct.productsByKindAndSpecieAndName(Integer.parseInt(idProductKind), Integer.parseInt(idSpecie), name, page);
			} else if (idSpecie != null) {
				return repositoryProduct.productsBySpecieAndName(Integer.parseInt(idSpecie), name, page);
			} else if (idProductKind != null) {
				return repositoryProduct.productsByKindAndName(Integer.parseInt(idProductKind), name, page);
			} else {
				return repositoryProduct.productsByName(name, page);
			}
			
		} else if (idSpecie != null && idProductKind != null) {
			return repositoryProduct.productsBySpecieAndKind(Integer.parseInt(idSpecie), Integer.parseInt(idProductKind), page);
		} else {
			return null;
		}
	}
	
	@Override
	public Product findById(int id) {
		Optional<Product> optional = repositoryProduct.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
	
	@Override
	public boolean existsById(int id) {
		return repositoryProduct.existsById(id);
	}
	
	@Override
	public void deleteById(int id) throws Exception {
		repositoryProduct.deleteById(id);
	}
	
	@Override
	public List<Product> findByIdIn(List<Integer> productsId){
		return repositoryProduct.findByIdIn(productsId);
	}
	
}
