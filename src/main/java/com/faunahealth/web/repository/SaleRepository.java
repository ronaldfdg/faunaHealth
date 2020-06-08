package com.faunahealth.web.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
	
	@Query(value = "select s from Sale s "
			+ "inner join fetch s.client "
			+ "where "
				+ "s.id = :saleId")
	Optional<Sale> findById(@Param("saleId") int saleId);
	
	@Query(value = "select s from Sale s "
			+ "inner join fetch s.client "
			+ "where "
				+ "s.client.id = :clientId "
				+ "order by s.saleDate desc")
	List<Sale> findByClient(@Param("clientId") int clientId, Pageable page);
	
	@Query(value = "select s from Sale s "
			+ "inner join fetch s.client "
			+ "where "
				+ "s.saleDate = :startDate "
				+ "order by s.saleDate desc")
	List<Sale> findByDate(@Param("startDate") Date startDate, Pageable page);
	
	@Query(value = "select s from Sale s "
			+ "inner join fetch s.client "
			+ "where "
				+ "s.saleDate between :startDate and :endDate "
				+ "order by s.saleDate desc")
	List<Sale> findBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable page);
	
}
