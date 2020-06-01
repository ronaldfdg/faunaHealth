package com.faunahealth.web.service;

import java.util.Date;
import java.util.List;

import com.faunahealth.web.entity.Appointment;

public interface AppointmentService {

	void save(Appointment appointment);

	List<Appointment> appointmentsByDate(Date date);

	Appointment findAppointmentByPatient(int patientId, Date nextAppointmentDate);

	Appointment findById(int id);
	
}