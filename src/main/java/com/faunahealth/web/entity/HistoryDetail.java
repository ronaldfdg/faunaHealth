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
@Table(name = "historia_detalle")
public class HistoryDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idHistoriaDetalle", nullable = false)
	private int id;
	
	@Column(name = "FechaAtencion", nullable = false)
	private Date attentionDate;
	
	@Column(name = "MotivoAtencion", nullable = false)
	private String reason;
	
	@Column(name = "ConstantesVitales", nullable = false)
	private String vitalSigns;
	
	@Column(name = "Diagnostico", nullable = false)
	private String diagnostic;
	
	@Column(name = "Receta")
	private String prescription;
	
	@Column(name = "Evolucion")
	private String evolution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuario")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idHistoriaClinica")
	private ClinicHistory clinicHistory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getAttentionDate() {
		return attentionDate;
	}

	public void setAttentionDate(Date attentionDate) {
		this.attentionDate = attentionDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getVitalSigns() {
		return vitalSigns;
	}

	public void setVitalSigns(String vitalSigns) {
		this.vitalSigns = vitalSigns;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getEvolution() {
		return evolution;
	}

	public void setEvolution(String evolution) {
		this.evolution = evolution;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ClinicHistory getClinicHistory() {
		return clinicHistory;
	}

	public void setClinicHistory(ClinicHistory clinicHistory) {
		this.clinicHistory = clinicHistory;
	}

	@Override
	public String toString() {
		return "HistoryDetail [id=" + id + ", attentionDate=" + attentionDate + ", reason=" + reason + ", vitalSigns="
				+ vitalSigns + ", diagnostic=" + diagnostic + ", prescription=" + prescription + ", evolution="
				+ evolution + "]";
	}
	
}
