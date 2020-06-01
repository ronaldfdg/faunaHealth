package com.faunahealth.web.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	public List<Appointment> appointmentsByDate(Date date) {
		return repositoryAppointment.appointmentsByDate(date);
	}

	@Override
	public Appointment findAppointmentByPatient(int patientId, Date nextAppointmentDate) {
		return repositoryAppointment.findAppointmentByPatient(patientId, nextAppointmentDate);
	}

	@Override
	public Appointment findById(int id) {
		Optional<Appointment> optional = repositoryAppointment.findById(id);
		if (optional.isPresent())
			return optional.get();
		return null;
	}

}