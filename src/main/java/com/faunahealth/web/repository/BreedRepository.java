package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faunahealth.web.entity.Breed;

public interface BreedRepository extends JpaRepository<Breed, Integer> {

	@Query("select b from Breed b where b.specie.id = 1")
	List<Breed> getBreedsDog(Sort sort);
	
	@Query("select b from Breed b where b.specie.id = 2")
	List<Breed> getBreedsCat(Sort sort);
	
	@Query("select b from Breed b where b.specie.id = 3")
	List<Breed> getBreedsRabbit(Sort sort);
	
}
