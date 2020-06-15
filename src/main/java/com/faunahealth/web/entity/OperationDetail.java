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
@Table(name = "cirugia_detalle")
public class OperationDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCirugiaDetalle")
	private int id;
	
	@Column(name = "FechaCirugia", nullable = false)
	private Date operationDate;
	
	@Column(name = "Temperatura")
	private double temperature;
	
	@Column(name = "FrecuenciaCardiaca")
	private double heartRate;
	
	@Column(name = "FrecuenciaRespiratoria")
	private double breathingFrequency;
	
	private String anamnesis;
	
	@Column(name = "Tratamiento")
	private String treatment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPaciente")
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCirugia")
	private Operation operation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(double heartRate) {
		this.heartRate = heartRate;
	}

	public double getBreathingFrequency() {
		return breathingFrequency;
	}

	public void setBreathingFrequency(double breathingFrequency) {
		this.breathingFrequency = breathingFrequency;
	}

	public String getAnamnesis() {
		return anamnesis;
	}

	public void setAnamnesis(String anamnesis) {
		this.anamnesis = anamnesis;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "OperationDetail [id=" + id + ", operationDate=" + operationDate + ", temperature=" + temperature
				+ ", heartRate=" + heartRate + ", breathingFrequency=" + breathingFrequency + ", anamnesis=" + anamnesis
				+ ", treatment=" + treatment + "]";
	}
	
}
