package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Breed;

public interface BreedRepository extends JpaRepository<Breed, Integer> {

	@Query("select b from Breed b "
			+ "where "
			+ "b.specie.id = :specieId "
			+ "order by b.name")
	List<Breed> getBreedsBySpecie(@Param("specieId") int specieId);
	Breed findById(int id);
	
}
