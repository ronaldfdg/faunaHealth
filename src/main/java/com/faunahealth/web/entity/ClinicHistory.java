package com.faunahealth.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "historiaclinica")
public class ClinicHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idHistoriaClinica")
	private int id;
	
	@Column(name = "FechaRegistro", nullable = false)
	private Date registrationDate;
	
	@Column(name = "Comportamiento")
	private String behavior;
	
	@Column(name = "DatosImportantes")
	private String importantInformation;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idPaciente")
	private Patient patient;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getBehavior() {
		return behavior;
	}

	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}

	public String getImportantInformation() {
		return importantInformation;
	}

	public void setImportantInformation(String importantInformation) {
		this.importantInformation = importantInformation;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "ClinicHistory [id=" + id + ", registrationDate=" + registrationDate + ", behavior=" + behavior
				+ ", importantInformation=" + importantInformation + "]";
	}
	
}
