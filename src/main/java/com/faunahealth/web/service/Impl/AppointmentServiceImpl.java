package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faunahealth.web.entity.Appointment;
import com.faunahealth.web.repository.AppointmentRepository;
import com.faunahealth.web.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository repositoryAppointment;
	
	@Override
	public void save(Appointment appointment) {
		repositoryAppointment.save(appointment);
	}

	@Override
	public List<Appointment> appointmentsToday(Date currentDate) {
		return repositoryAppointment.appointmentsToday(currentDate);
	}

	@Override
	public Appointment findAppointmentByPatient(int patientId) {
		return repositoryAppointment.findAppointmentByPatient(patientId);
	}

}
