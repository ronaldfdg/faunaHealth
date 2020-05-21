package com.faunahealth.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

}
