package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	@Query(value = "select c from Client c "
			+ "inner join fetch c.district "
			+ "where "
			+ "c.documentNumber = :documentNumber"
			, countQuery = "select count (c.id) from Client c")
	Page<Client> findByDocumentNumber(@Param("documentNumber") String documentNumber, Pageable page);
	
	@Query(value = "select c from Client c "
			+ "inner join fetch c.district "
			+ "where "
			+ "c.name like concat ('%', :name, '%') "
			+ "order by c.name asc"
			, countQuery = "select count (c.id) from Client c")
	Page<Client> findClientsByNameAndPage(@Param("name") String name, Pageable page);
	List<Client> findByNameContaining(String name);
	
	@Query(value = "select c from Client c "
			+ "inner join fetch c.district "
			+ "where "
			+ "c.primaryLastName like concat ('%', :primaryLastName, '%') "
			+ "order by c.name asc"
			, countQuery = "select count (c.id) from Client c")
	Page<Client> findClientsByPrimaryLastNameAndPage(@Param("primaryLastName")String primaryLastName, Pageable page);
	List<Client> findByPrimaryLastNameContaining(String primaryLastName);
	
	@Query(value = "select c from Client c "
			+ "inner join fetch c.district "
			+ "where "
			+ "c.name like concat ('%', :name, '%') "
			+ "and "
			+ "c.primaryLastName like concat ('%', :primaryLastName, '%') "
			+ "order by c.name asc"
			, countQuery = "select count (c.id) from Client c")
	Page<Client> findClientsByNameAndPrimaryLastNameAndPage(@Param("name") String name, @Param("primaryLastName") String primaryLastName, Pageable page);
	List<Client> findByNameContainingAndPrimaryLastNameContaining(String name, String primaryLastName);
	
}
