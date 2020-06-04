package com.faunahealth.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

}
