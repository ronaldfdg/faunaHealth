package com.faunahealth.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faunahealth.web.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
