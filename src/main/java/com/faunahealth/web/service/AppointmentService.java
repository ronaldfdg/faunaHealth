package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.faunahealth.web.entity.Appointment;

public interface AppointmentService {

	void save(Appointment appointment);
	List<Appointment> appointmentsToday(@Param("currentDate") Date currentDate);
	Appointment findAppointmentByPatient(int patientId);
}
