package com.faunahealth.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "citas")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCita")
	private int id;
	
	@Column(name = "ProximaCita", nullable = false)
	private Date nextAppointmentDate;
	
	@Column(name = "Motivo", nullable = false)
	private String reason;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPaciente")
	private Patient patient;
	
	@Column(name = "Confirmacion", nullable = false)
	private boolean confirmation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getNextAppointmentDate() {
		return nextAppointmentDate;
	}

	public void setNextAppointmentDate(Date nextAppointmentDate) {
		this.nextAppointmentDate = nextAppointmentDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", nextAppointmentDate=" + nextAppointmentDate + ", reason=" + reason
				+ ", patient=" + patient + ", confirmation=" + confirmation + "]";
	}
	
}