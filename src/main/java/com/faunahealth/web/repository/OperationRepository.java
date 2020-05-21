package com.faunahealth.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.Operation;

public interface OperationRepository extends JpaRepository<Operation, Integer>{

}
