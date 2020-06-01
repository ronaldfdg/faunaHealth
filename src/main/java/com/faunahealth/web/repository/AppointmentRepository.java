package com.faunahealth.web.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query("select a from Appointment a " + "inner join fetch a.patient " + "where " + "a.nextAppointmentDate = :date "
			+ "order by a.patient.nickname desc")
	List<Appointment> appointmentsByDate(@Param("date") Date date);

	@Query("select a from Appointment a " + "inner join fetch a.patient " + "where " + "a.patient.id = :patientId "
			+ "and " + "a.nextAppointmentDate = :nextAppointmentDate")
	Appointment findAppointmentByPatient(@Param("patientId") int patientId, @Param("nextAppointmentDate") Date nextAppointmentDate);
	
}