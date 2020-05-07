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
@Table(name = "peso")
public class Weight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPeso")
	private int id;
	
	@Column(name = "Peso", nullable = false)
	private double amount;
	
	@Column(name = "FechaPeso", nullable = false)
	private Date registrationDate = new Date();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPaciente")
	private Patient patient;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Weight [id=" + id + ", amount=" + amount + ", registrationDate=" + registrationDate + "]";
	}
	
}
