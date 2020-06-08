package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.SaleDetail;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Integer> {
	
	@Query("select s from SaleDetail s "
			+ "inner join fetch s.product "
			+ "where "
				+ "s.sale.id = :saleId "
				+ "order by s.subtotal desc")
	List<SaleDetail> findBySale_Id(@Param("saleId") int saleId);
	
}
