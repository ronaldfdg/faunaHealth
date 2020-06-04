package com.faunahealth.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.ProductKind;

public interface ProductKindRepository extends JpaRepository<ProductKind, Integer> {

}
