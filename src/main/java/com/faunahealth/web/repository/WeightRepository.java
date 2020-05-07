package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faunahealth.web.entity.Weight;

public interface WeightRepository extends JpaRepository<Weight, Integer> {

	@Query("select w from Weight w "
			+ "inner join fetch w.patient "
			+ "where w.patient.id = ?1 "
			+ "order by w.registrationDate desc")
	List<Weight> getWeightsByPatient(int id);
	
}
