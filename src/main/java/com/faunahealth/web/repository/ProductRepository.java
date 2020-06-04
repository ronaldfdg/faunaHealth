package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(value = "select p from Product p "
							+ "inner join fetch p.specie "
							+ "inner join fetch p.productKind "
							+ "where "
								+ "p.productKind.id = :idProductKind "
								+ "and "
								+ "p.specie.id = :idSpecie "
								+ "and "
								+ "p.name like concat ('%', :name, '%')")
	List<Product> productsByKindAndSpecieAndName(@Param("idProductKind") int idProductKind, @Param("idSpecie") int idSpecie, @Param("name") String name, Pageable page);
	
	@Query(value = "select p from Product p "
							+ "inner join fetch p.specie "
							+ "inner join fetch p.productKind "
							+ "where "
								+ "p.specie.id = :idSpecie "
								+ "and "
								+ "p.name like concat ('%', :name, '%')")
	List<Product> productsBySpecieAndName(@Param("idSpecie") int idSpecie, @Param("name") String name, Pageable page);
	
	@Query(value = "select p from Product p "
							+ "inner join fetch p.specie "
							+ "inner join fetch p.productKind "
							+ "where "
								+ "p.productKind.id = :idProductKind "
								+ "and "
								+ "p.name like concat ('%', :name, '%')")
	List<Product> productsByKindAndName(@Param("idProductKind") int idProductKind, @Param("name") String name, Pageable page);
	
	@Query(value = "select p from Product p "
							+ "inner join fetch p.specie "
							+ "inner join fetch p.productKind "
							+ "where "
								+ "p.specie.id = :idSpecie "
								+ "and "
								+ "p.productKind.id = :idProductKind")
	List<Product> productsBySpecieAndKind(@Param("idSpecie") int idSpecie, @Param("idProductKind") int idProductKind, Pageable page);
	
	@Query(value = "select p from Product p "
							+ "inner join fetch p.specie "
							+ "inner join fetch p.productKind "
							+ "where "
								+ "p.name like concat ('%', :name, '%')")
	List<Product> productsByName(@Param("name") String name, Pageable page);
	
	List<Product> findByIdIn(List<Integer> productsId);
	
}
