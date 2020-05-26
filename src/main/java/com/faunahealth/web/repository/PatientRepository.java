package com.faunahealth.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer>{

	@Query("select p from Patient p inner join fetch p.client")
	List<Patient> getPatients(Sort sort);
	
	@Query(value = "select p from Patient p "
			+ "inner join fetch p.client "
			+ "where p.client.id = :clientId "
			+ "order by "
			+ "p.nickname asc"
			, countQuery = "select count (p.id) from Patient p")
	Page<Patient> findPatientsByClientAndPage(@Param("clientId") int clientId, Pageable page);
	
	@Query("select p from Patient p inner join fetch p.client where p.client.id = ?1")
	List<Patient> getPatientsByClient(int clientId, Sort sort);
	
	@Query(value = "select p from Patient p "
			+ "inner join fetch p.client "
			+ "where "
			+ "p.nickname like concat ('%', :nickname, '%') "
			+ "and "
			+ "p.client.primaryLastName like concat ('%', :primaryLastName, '%') "
			+ "order by "
			+ "p.nickname asc"
			, countQuery = "select count (p.id) from Patient p")
	Page<Patient> findPatientsByNicknameAndPrimaryLastNameAndPage(@Param("nickname") String nickname, @Param("primaryLastName") String primaryLastName, Pageable page);
	
	@Query("select p from Patient p inner join fetch p.client "
			+ "where p.nickname like concat('%', ?1, '%') and p.client.primaryLastName like concat('%', ?2, '%')")
	List<Patient> getPatientsByNicknameAndLastNameClient(String nickname, String primaryLastName);
	
	@Query(value = "select p from Patient p "
			+ "inner join fetch p.client "
			+ "where "
			+ "p.nickname like concat ('%', :nickname, '%') "
			+ "order by "
			+ "p.nickname asc"
			, countQuery = "select count (p.id) from Patient p")
	Page<Patient> findPatientsByNicknameAndPage(@Param("nickname") String nickname, Pageable page);
	List<Patient> findByNicknameContaining(String nickname);
	
	@Query(value = "select p from Patient p "
			+ "inner join fetch p.client "
			+ "where "
			+ "p.client.primaryLastName like concat ('%', :primaryLastName, '%') "
			+ "order by "
			+ "p.nickname asc"
			, countQuery = "select count (p.id) from Patient p")
	Page<Patient> findPatientsByPrimaryLastNameAndPage(@Param("primaryLastName") String primaryLastName, Pageable page);
	List<Patient> findByClient_PrimaryLastNameContaining(String primaryLastName);
	
}
