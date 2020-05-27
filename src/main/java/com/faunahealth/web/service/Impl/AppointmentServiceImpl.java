package com.faunahealth.web.service.Impl;

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

}
